package com.ticket.controller.user;

import com.ticket.result.Result;
import com.ticket.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 模拟支付成功
     */
    @PostMapping("/{orderNo}/success")
    public Result<Void> paySuccess(@PathVariable String orderNo) {
        paymentService.paySuccess(orderNo);
        return Result.success();
    }
}