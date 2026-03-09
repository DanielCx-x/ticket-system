package com.ticket.service;

import com.ticket.vo.TicketTierVO;
import java.util.List;

public interface TicketTierService {

    /**
     * 查询指定活动下的票档列表
     */
    List<TicketTierVO> listByEventId(Long eventId);
}
