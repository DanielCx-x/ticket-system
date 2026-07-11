package com.ticket.entity;

import com.ticket.enums.PaymentStatusEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付记录实体类，对应 payment_record 表。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecord {

    // 支付记录主键 ID
    private Long id;

    // 支付流水号
    private String paymentNo;

    // 订单号
    private String orderNo;

    // 支付用户 ID
    private Long userId;

    // 支付金额
    private BigDecimal amount;

    // 支付状态
    private PaymentStatusEnum status;

    // 支付成功时间
    private LocalDateTime payTime;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}