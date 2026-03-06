package com.ticket.vo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提交订单后返回给前端的视图对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitVO {

    // 订单号
    private String orderNo;

    // 订单状态描述
    private String orderStatus;

    // 订单金额
    private BigDecimal amount;
}
