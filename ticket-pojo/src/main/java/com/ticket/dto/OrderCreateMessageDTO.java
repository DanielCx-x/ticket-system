package com.ticket.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下单异步创建订单消息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 系统内部发给 Kafka 的异步创建订单消息
public class OrderCreateMessageDTO {

    private String orderNo;

    private Long userId;

    private Long eventId;

    private Long ticketTierId;

    private Integer ticketCount;

    private BigDecimal amount;
}