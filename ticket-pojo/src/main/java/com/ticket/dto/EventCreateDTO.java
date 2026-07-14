package com.ticket.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理端新增活动请求 DTO。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateDTO {

    // 演出名称
    private String name;

    // 演出场馆
    private String venue;

    // 演出时间
    private LocalDateTime showTime;

    // 状态：0-未开售，1-售票中
    private Integer status;
}