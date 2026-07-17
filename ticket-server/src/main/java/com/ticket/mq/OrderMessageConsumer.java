package com.ticket.mq;

import com.ticket.dto.OrderCreateMessageDTO;
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
        int stockRows = ticketTierMapper.deductStock(
                messageDTO.getTicketTierId(),
                messageDTO.getTicketCount()
        );

        if (stockRows == 0) {
            ticketOrderMapper.updateStatus(
                messageDTO.getOrderNo(),
                OrderStatusEnum.QUEUED,
                OrderStatusEnum.FAILED,
                LocalDateTime.now()
            );

            stockRedisService.rollbackStock(
                messageDTO.getTicketTierId(),
                messageDTO.getTicketCount()
            );

            return;
        }

        int orderRows = ticketOrderMapper.updateStatus(
                messageDTO.getOrderNo(),
                OrderStatusEnum.QUEUED,
                OrderStatusEnum.CONFIRMED,
                LocalDateTime.now()
        );

        if (orderRows == 0) {
            throw new BaseException("订单状态更新失败");
        }
    }
}