package com.ticket.controller.admin;

import com.ticket.result.Result;
import com.ticket.service.TicketOrderService;
import com.ticket.vo.OrderDetailVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final TicketOrderService ticketOrderService;

    /**
     * 管理端查询全部订单。
     */
    @GetMapping
    public Result<List<OrderDetailVO>> listAll() {
        return Result.success(ticketOrderService.listAllOrders());
    }

    /**
     * 管理端查询订单详情。
     */
    @GetMapping("/{orderNo}")
    public Result<OrderDetailVO> getByOrderNo(@PathVariable String orderNo) {
        return Result.success(ticketOrderService.getAdminOrderDetail(orderNo));
    }
}