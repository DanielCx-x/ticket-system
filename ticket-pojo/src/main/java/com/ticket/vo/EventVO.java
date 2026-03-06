package com.ticket.vo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventVO {

    private Long id;

    private String name;

    private String venue;

    private LocalDateTime showTime;

    private String statusDesc;
}
