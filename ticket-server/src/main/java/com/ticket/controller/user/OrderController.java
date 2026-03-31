package com.ticket.controller.user;

import com.ticket.dto.TicketOrderSubmitDTO;
import com.ticket.result.Result;
import com.ticket.service.TicketOrderService;
import com.ticket.vo.OrderDetailVO;
import com.ticket.vo.OrderSubmitVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/orders")
@RequiredArgsConstructor
public class OrderController {

    private final TicketOrderService ticketOrderService;

    /**
     * 用户提交订单
     */
    @PostMapping
    public Result<OrderSubmitVO> submit(@RequestBody TicketOrderSubmitDTO ticketOrderSubmitDTO) {
        OrderSubmitVO orderSubmitVO = ticketOrderService.submit(ticketOrderSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    @GetMapping("/{orderNo}")
    public Result<OrderDetailVO> getByOrderNo(@PathVariable String orderNo) {
        return Result.success(ticketOrderService.getByOrderNo(orderNo));
    }
}
