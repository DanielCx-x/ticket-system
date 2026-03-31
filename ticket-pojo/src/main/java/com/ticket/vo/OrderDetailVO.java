package com.ticket.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单详情视图对象，返回给前端展示订单详细信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO {

    // 订单号
    private String orderNo;

    // 下单用户ID
    private Long userId;

    // 活动ID
    private Long eventId;

    // 票档ID
    private Long ticketTierId;

    // 购票数量
    private Integer ticketCount;

    // 订单总金额
    private BigDecimal amount;

    // 订单状态
    private String orderStatus;

    // 下单时间
    private LocalDateTime createTime;
}
