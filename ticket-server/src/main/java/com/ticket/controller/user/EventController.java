package com.ticket.controller.user;

import com.ticket.result.Result;
import com.ticket.service.EventService;
import com.ticket.service.TicketTierService;
import com.ticket.vo.EventVO;
import com.ticket.vo.TicketTierVO;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final TicketTierService ticketTierService;

    @GetMapping
    public Result<List<EventVO>> list() {
        return Result.success(eventService.list());
    }

    @GetMapping("/{id}")
    public Result<EventVO> getEventDetail(@PathVariable Long id) {
        return Result.success(eventService.getEventDetail(id));
    }

    @GetMapping("/{eventId}/tiers")
    public Result<List<TicketTierVO>> listTiers(@PathVariable Long eventId) {
        return Result.success(ticketTierService.listByEventId(eventId));
    }
}
