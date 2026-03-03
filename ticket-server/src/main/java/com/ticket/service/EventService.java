package com.ticket.service;

import com.ticket.vo.EventVO;

public interface EventService {
    /**
     * 根据活动ID获取详情
     */
    EventVO getEventDetail(Long id);
}