package com.ticket.service;

import com.ticket.dto.TicketOrderSubmitDTO;
import com.ticket.vo.OrderDetailVO;
import com.ticket.vo.OrderSubmitVO;
import java.util.List;

public interface TicketOrderService {

    /**
     * 提交订单
     */
    OrderSubmitVO submit(TicketOrderSubmitDTO ticketOrderSubmitDTO);

    OrderDetailVO getByOrderNo(String orderNo);

    void cancelByOrderNo(String orderNo);

    List<OrderDetailVO> listCurrentUserOrders();
}
