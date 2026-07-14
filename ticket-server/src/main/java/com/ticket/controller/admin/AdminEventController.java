package com.ticket.controller.admin;

import com.ticket.result.Result;
import com.ticket.service.EventService;
import com.ticket.vo.EventVO;
import com.ticket.dto.EventCreateDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final EventService eventService;

    /**
     * 管理端查询活动列表。
     */
    @GetMapping
    public Result<List<EventVO>> list() {
        return Result.success(eventService.list());
    }

    /**
     * 管理端查询活动详情。
     */
    @GetMapping("/{id}")
    public Result<EventVO> getById(@PathVariable Long id) {
        return Result.success(eventService.getEventDetail(id));
    }

    /**
     * 管理端新增活动。
     */
    @PostMapping
    public Result<Void> create(@RequestBody EventCreateDTO eventCreateDTO) {
        eventService.create(eventCreateDTO);
        return Result.success();
    }

    /**
     * 管理端修改活动状态。
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        eventService.updateStatus(id, status);
        return Result.success();
    }
}