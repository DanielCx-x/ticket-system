package com.ticket.entity;

import com.ticket.enums.OrderStateEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 票务订单实体类，对应 ticket_order 表。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketOrder {

    // 订单主键 ID
    private Long id;

    // 全局唯一订单号
    private String orderNo;

    // 下单用户 ID
    private Long userId;

    // 演出活动 ID
    private Long eventId;

    // 票档 ID
    private Long ticketTierId;

    // 购票数量
    private Integer ticketCount;

    // 订单总金额
    private BigDecimal amount;

    // 订单状态
    private OrderStateEnum status;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
