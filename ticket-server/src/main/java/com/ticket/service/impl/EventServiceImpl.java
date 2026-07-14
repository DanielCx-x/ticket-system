package com.ticket.service.impl;

import com.ticket.entity.Event;
import com.ticket.exception.EventNotFoundException;
import com.ticket.mapper.EventMapper;
import com.ticket.service.EventService;
import com.ticket.vo.EventVO;
import com.ticket.dto.EventCreateDTO;
import com.ticket.exception.BaseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public void create(EventCreateDTO eventCreateDTO) {
        if (eventCreateDTO == null
                || !StringUtils.hasText(eventCreateDTO.getName())
                || !StringUtils.hasText(eventCreateDTO.getVenue())
                || eventCreateDTO.getShowTime() == null) {
            throw new BaseException("活动信息不能为空");
        }

        Integer status = eventCreateDTO.getStatus();
        if (status == null) {
            status = 0;
        }

        if (status != 0 && status != 1) {
            throw new BaseException("活动状态不合法");
        }

        Event event = Event.builder()
            .name(eventCreateDTO.getName())
            .venue(eventCreateDTO.getVenue())
            .showTime(eventCreateDTO.getShowTime())
            .status(status)
            .build();

        eventMapper.insert(event);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        if (id == null) {
            throw new BaseException("活动ID不能为空");
        }

        if (status == null || (status != 0 && status != 1)) {
            throw new BaseException("活动状态不合法");
        }

        int rows = eventMapper.updateStatus(id, status);
        if (rows == 0) {
            throw new EventNotFoundException("活动不存在");
        }
    }
}
