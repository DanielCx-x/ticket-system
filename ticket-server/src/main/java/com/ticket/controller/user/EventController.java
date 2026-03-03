package com.ticket.controller.user;

import com.ticket.result.Result;
import com.ticket.service.EventService;
import com.ticket.vo.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/event")
public class EventController {
    @Autowired
    private EventService eventService;

    /**
     * 根据活动ID获取详情
     * @param id 活动ID
     * @return 活动详情视图对象
     */
    @GetMapping("/{id}")
    public Result<EventVO> getEventDetail(@PathVariable Long id) {
        EventVO eventVO = eventService.getEventDetail(id);
        return Result.success(eventVO);
    }
}
