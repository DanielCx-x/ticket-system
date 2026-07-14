package com.ticket.controller.admin;

import com.ticket.result.Result;
import com.ticket.service.TicketTierService;
import com.ticket.vo.TicketTierVO;
import com.ticket.dto.TicketTierCreateDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin/events/{eventId}/tiers")
@RequiredArgsConstructor
public class AdminTicketTierController {

    private final TicketTierService ticketTierService;

    /**
     * 管理端查询活动下的票档列表。
     */
    @GetMapping
    public Result<List<TicketTierVO>> listByEventId(@PathVariable Long eventId) {
        return Result.success(ticketTierService.listByEventId(eventId));
    }

    /**
     * 管理端新增票档。
     */
    @PostMapping
    public Result<Void> create(@PathVariable Long eventId,
                            @RequestBody TicketTierCreateDTO ticketTierCreateDTO) {
        ticketTierService.create(eventId, ticketTierCreateDTO);
        return Result.success();
    }
}
