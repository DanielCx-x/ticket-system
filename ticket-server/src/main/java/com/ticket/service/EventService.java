package com.ticket.service;

import java.util.List;

import com.ticket.vo.EventVO;

public interface EventService {
    
    /**
     * 查询活动列表
     */
    List<EventVO> list();
    
    /**
     * 根据活动ID获取详情
     */
    EventVO getEventDetail(Long id);
}