package com.ticket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 活动/演出视图对象，返回给前端展示用
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EventVO {
    // 活动ID
    private Long id;

    // 活动名称
    private String name;

    // 演出场馆
    private String venue;

    // 演出时间
    private LocalDateTime showTime;

    // 活动状态描述
    private String statusDesc;
}
