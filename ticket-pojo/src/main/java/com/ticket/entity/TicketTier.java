package com.ticket.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 票档实体类，对应 ticket_tier 表。
 * 一个演出活动下可以有多个票档，例如 VIP、看台 A 区。
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketTier {
    
    // 票档主键 ID
    private Long id;

    //关联的演出活动 ID
    private Long eventId;

    // 票档名称
    private String tierName;

    // 票档价格
    private BigDecimal price;

    // 总库存
    private Integer totalStock;

    // 当前可用库存
    private Integer availableStock;

    //乐观锁版本号
    private Integer version;
}
