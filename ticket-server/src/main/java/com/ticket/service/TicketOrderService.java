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

    OrderDetailVO getUserOrderDetail(String orderNo);

    OrderDetailVO getAdminOrderDetail(String orderNo);

    void cancelByOrderNo(String orderNo);

    List<OrderDetailVO> listCurrentUserOrders();

    /**
     * 管理端查询全部订单。
     */
    List<OrderDetailVO> listAllOrders();
}
