package com.ticket.mq;

import com.ticket.dto.OrderCreateMessageDTO;
import com.ticket.entity.TicketOrder;
import com.ticket.enums.OrderStatusEnum;
import com.ticket.exception.BaseException;
import com.ticket.mapper.TicketOrderMapper;
import com.ticket.mapper.TicketTierMapper;
import com.ticket.service.StockRedisService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单消息消费者。
 */
@Component
@RequiredArgsConstructor
public class OrderMessageConsumer {

    private final TicketOrderMapper ticketOrderMapper;
    private final TicketTierMapper ticketTierMapper;
    private final StockRedisService stockRedisService;

    @KafkaListener(
            topics = OrderMessageProducer.ORDER_CREATE_TOPIC,
            groupId = "ticket-order-group"
    )
    @Transactional
    public void consumeCreateOrderMessage(OrderCreateMessageDTO messageDTO) {
        TicketOrder ticketOrder = ticketOrderMapper.getByOrderNo(messageDTO.getOrderNo());
        if (ticketOrder == null) {
            return;
        }

        // 查到 QUEUED
        if (ticketOrder.getStatus() != OrderStatusEnum.QUEUED) {
            return;
        }
        
        // 先 QUEUED -> PROCESSING
        int processingRows = ticketOrderMapper.updateStatus(
            messageDTO.getOrderNo(),
            OrderStatusEnum.QUEUED,
            OrderStatusEnum.PROCESSING,
            LocalDateTime.now() 
        );

        // 如果更新成功，说明抢到处理权
        if (processingRows == 0) {
            return;
        }

        // 扣 MySQL 库存
        int stockRows = ticketTierMapper.deductStock(
                messageDTO.getTicketTierId(),
                messageDTO.getTicketCount()
        );

        // 失败则 PROCESSING -> FAILED
        if (stockRows == 0) {
            ticketOrderMapper.updateStatus(
                messageDTO.getOrderNo(),
                OrderStatusEnum.PROCESSING,
                OrderStatusEnum.FAILED,
                LocalDateTime.now()
            );

            stockRedisService.rollbackStock(
                messageDTO.getTicketTierId(),
                messageDTO.getTicketCount()
            );

            return;
        }
        
        int stockDeductedRows = ticketOrderMapper.updateStatus(
            messageDTO.getOrderNo(),
            OrderStatusEnum.PROCESSING,
            OrderStatusEnum.STOCK_DEDUCTED,
            LocalDateTime.now()
        );

        if (stockDeductedRows == 0) {
            throw new BaseException("订单库存扣减状态更新失败");
        }

        int confirmedRows = ticketOrderMapper.updateStatus(
            messageDTO.getOrderNo(),
            OrderStatusEnum.STOCK_DEDUCTED,
            OrderStatusEnum.CONFIRMED,
            LocalDateTime.now()
        );

        if (confirmedRows == 0) {
            throw new BaseException("订单确认状态更新失败");
        }
    }
}