package com.ticket.service;

import java.util.List;

import com.ticket.dto.EventCreateDTO;
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

    /**
     * 管理端新增活动。
     */
    void create(EventCreateDTO eventCreateDTO);

    /**
     * 管理端修改活动状态。
     */
    void updateStatus(Long id, Integer status);
}