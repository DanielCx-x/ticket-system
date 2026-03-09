package com.ticket.service;

import com.ticket.dto.TicketOrderSubmitDTO;
import com.ticket.vo.OrderSubmitVO;

public interface TicketOrderService {

    /**
     * 提交订单
     */
    OrderSubmitVO submit(TicketOrderSubmitDTO ticketOrderSubmitDTO);
}
