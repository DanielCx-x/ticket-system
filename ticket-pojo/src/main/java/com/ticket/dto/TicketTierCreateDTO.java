package com.ticket.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理端新增票档请求 DTO。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketTierCreateDTO {

    // 票档名称
    private String tierName;

    // 票档价格
    private BigDecimal price;

    // 总库存
    private Integer totalStock;
}