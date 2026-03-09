package com.ticket.vo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 票档视图对象，返回给前端展示。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketTierVO {

    // 票档ID
    private Long id;

    // 所属活动ID
    private Long eventId;

    // 票档名称
    private String tierName;

    // 票价
    private BigDecimal price;

    // 当前可用库存
    private Integer availableStock;
}
