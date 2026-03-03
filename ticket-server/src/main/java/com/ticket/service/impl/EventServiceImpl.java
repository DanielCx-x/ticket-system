package com.ticket.service.impl;

import com.ticket.service.EventService;
import com.ticket.vo.EventVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventServiceImpl implements EventService {

    @Override
    public EventVO getEventDetail(Long id) {
        // TODO: 实际业务逻辑应从数据库查询，此处为模拟数据返回
        return EventVO.builder()
                .id(id)
                .name("示例演出活动")
                .venue("国家大剧院")
                .showTime(LocalDateTime.now().plusDays(7)) // 模拟7天后的演出
                .statusDesc("售票中")
                .build();
    }    
}
