package com.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提交订单时前端传给后端的数据传输对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketOrderSubmitDTO {

    // 下单用户 ID
    private Long userId;

    // 演出活动 ID
    private Long eventId;

    // 选择的票档 ID
    private Long ticketTierId;

    // 购买张数
    private Integer ticketCount;
}
