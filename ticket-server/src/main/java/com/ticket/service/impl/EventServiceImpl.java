package com.ticket.service.impl;

import com.ticket.entity.Event;
import com.ticket.exception.EventNotFoundException;
import com.ticket.mapper.EventMapper;
import com.ticket.service.EventService;
import com.ticket.vo.EventVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    
    /**
     * 根据活动ID获取详情
     */
    @Override
    public EventVO getEventDetail(Long eventId) {
        Event event = eventMapper.getById(eventId);
        if (event == null) {
            throw new EventNotFoundException("活动不存在");
        }

        String statusDesc = event.getStatus() != null && event.getStatus() == 1 ? "售票中" : "未开售";

        return EventVO.builder()
                .id(event.getId())
                .name(event.getName())
                .venue(event.getVenue())
                .showTime(event.getShowTime())
                .statusDesc(statusDesc)
                .build();
    }    
}
