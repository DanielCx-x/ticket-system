package com.ticket.mq;

import com.ticket.dto.OrderCreateMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 订单消息生产者。
 */
@Component
@RequiredArgsConstructor
public class OrderMessageProducer {

    public static final String ORDER_CREATE_TOPIC = "ticket-order-create-topic";

    private final KafkaTemplate<String, OrderCreateMessageDTO> kafkaTemplate;

    public void sendCreateOrderMessage(OrderCreateMessageDTO messageDTO) {
        kafkaTemplate.send(ORDER_CREATE_TOPIC, messageDTO.getOrderNo(), messageDTO).join();
    }
}