package com.ticket.service.impl;

import com.ticket.context.BaseContext;
import com.ticket.entity.PaymentRecord;
import com.ticket.entity.TicketOrder;
import com.ticket.enums.OrderStateEnum;
import com.ticket.enums.PaymentStatusEnum;
import com.ticket.exception.BaseException;
import com.ticket.mapper.PaymentRecordMapper;
import com.ticket.mapper.TicketOrderMapper;
import com.ticket.service.PaymentService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRecordMapper paymentRecordMapper;
    private final TicketOrderMapper ticketOrderMapper;

    @Override
    @Transactional
    public void paySuccess(String orderNo) {
        Long currentUserId = getCurrentUserId();

        TicketOrder ticketOrder = ticketOrderMapper.getByOrderNo(orderNo);
        if (ticketOrder == null) {
            throw new BaseException("订单不存在");
        }

        if (!ticketOrder.getUserId().equals(currentUserId)) {
            throw new BaseException("无权支付该订单");
        }

        if (ticketOrder.getStatus() != OrderStateEnum.CONFIRMED) {
            throw new BaseException("当前订单状态不允许支付");
        }

        PaymentRecord oldPaymentRecord = paymentRecordMapper.getByOrderNo(orderNo);
        if (oldPaymentRecord != null) {
            throw new BaseException("该订单已存在支付记录");
        }

        LocalDateTime now = LocalDateTime.now();

        PaymentRecord paymentRecord = PaymentRecord.builder()
                .paymentNo(generatePaymentNo())
                .orderNo(orderNo)
                .userId(currentUserId)
                .amount(ticketOrder.getAmount())
                .status(PaymentStatusEnum.SUCCESS)
                .payTime(now)
                .createTime(now)
                .updateTime(now)
                .build();

        paymentRecordMapper.insert(paymentRecord);

        int rows = ticketOrderMapper.updateStatus(
                orderNo,
                OrderStateEnum.CONFIRMED,
                OrderStateEnum.PAID,
                now
        );

        if (rows == 0) {
            throw new BaseException("订单状态已变化，请刷新后重试");
        }
    }

    private Long getCurrentUserId() {
        Long currentUserId = BaseContext.getCurrentId();
        if (currentUserId == null) {
            throw new BaseException("用户未登录");
        }
        return currentUserId;
    }

    private String generatePaymentNo() {
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return "P" + System.currentTimeMillis() + randomPart;
    }
}