package com.ticket.service;

import com.ticket.vo.TicketTierVO;
import com.ticket.dto.TicketTierCreateDTO;
import java.util.List;

public interface TicketTierService {

    /**
     * 查询指定活动下的票档列表
     */
    List<TicketTierVO> listByEventId(Long eventId);

    /**
     * 管理端新增票档。
     */
    void create(Long eventId, TicketTierCreateDTO ticketTierCreateDTO);

    /**
     * 管理端修改票档库存。
     */
    void updateStock(Long tierId, Integer totalStock);
}
