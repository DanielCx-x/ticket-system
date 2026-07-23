package com.ticket.task;

import com.ticket.entity.TicketOrder;
import com.ticket.enums.OrderStatusEnum;
import com.ticket.mapper.TicketOrderMapper;
import com.ticket.mapper.TicketTierMapper;
import com.ticket.service.StockRedisService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCompensationTask {

    private static final int TIMEOUT_MINUTES = 10; // 查找超过 10 分钟还停在 QUEUED / PROCESSING / STOCK_DEDUCTED 的订单
    private static final int BATCH_SIZE = 100;

    private final TicketOrderMapper ticketOrderMapper;
    private final StockRedisService stockRedisService;
    private final TicketTierMapper ticketTierMapper;
    private final RedissonClient redissonClient;

    /**
     * 定期补偿长时间停留在 QUEUED / PROCESSING / STOCK_DEDUCTED 的订单。
     */
    @Scheduled(fixedDelay = 60000) // 每 60 秒执行一次
    public void compensateTimeoutOrders() {
        RLock lock = redissonClient.getLock("lock:order:compensation");

        boolean locked;
        try {
            locked = lock.tryLock(0, 55, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        if (!locked) {
            return;
        }

        try {
            LocalDateTime timeoutTime = LocalDateTime.now().minusMinutes(TIMEOUT_MINUTES);

            List<TicketOrder> timeoutOrders = ticketOrderMapper.listTimeoutProcessingOrders(
                    timeoutTime,
                    BATCH_SIZE
            );

            for (TicketOrder ticketOrder : timeoutOrders) {
                OrderStatusEnum oldStatus = ticketOrder.getStatus();
                int rows = ticketOrderMapper.updateStatus(
                        ticketOrder.getOrderNo(),
                        oldStatus,
                        OrderStatusEnum.FAILED,
                        LocalDateTime.now()
                );

                if (rows == 0) {
                    continue;
                }

                stockRedisService.rollbackStock(
                        ticketOrder.getTicketTierId(),
                        ticketOrder.getTicketCount()
                );

                if (oldStatus == OrderStatusEnum.STOCK_DEDUCTED) {
                    int stockRows = ticketTierMapper.increaseStock(
                        ticketOrder.getTicketTierId(),
                        ticketOrder.getTicketCount()
                    );

                    if (stockRows == 0) {
                        continue;
                    }
                }
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}