package com.ticket.service.impl;

import com.ticket.entity.Event;
import com.ticket.exception.EventNotFoundException;
import com.ticket.mapper.EventMapper;
import com.ticket.service.EventService;
import com.ticket.vo.EventVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;

    @Override
    public List<EventVO> list() {
        return eventMapper.list().stream()
                .map(this::toEventVO)
                .collect(Collectors.toList());
    }

    @Override
    public EventVO getEventDetail(Long eventId) {
        Event event = eventMapper.getById(eventId);
        if (event == null) {
            throw new EventNotFoundException("活动不存在");
        }
        return toEventVO(event);
    }

    private EventVO toEventVO(Event event) {
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
