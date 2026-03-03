package com.ticket.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 演出活动实体，对应 event 表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 演出名称
     */
    private String name;

    /**
     * 演出场馆
     */
    private String venue;

    /**
     * 演出时间
     */
    private LocalDateTime showTime;

    /**
     * 状态：0-未开售，1-售票中
     */
    private Integer status;
}