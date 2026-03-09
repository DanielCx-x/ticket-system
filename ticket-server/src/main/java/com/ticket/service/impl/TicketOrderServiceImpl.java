package com.ticket.service.impl;

import com.ticket.dto.TicketOrderSubmitDTO;
import com.ticket.entity.TicketOrder;
import com.ticket.entity.TicketTier;
import com.ticket.enums.OrderStateEnum;
import com.ticket.exception.BaseException;
import com.ticket.exception.StockNotEnoughException;
import com.ticket.mapper.TicketOrderMapper;
import com.ticket.mapper.TicketTierMapper;
import com.ticket.service.TicketOrderService;
import com.ticket.vo.OrderSubmitVO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketOrderServiceImpl implements TicketOrderService {

    private final TicketTierMapper ticketTierMapper;
    private final TicketOrderMapper ticketOrderMapper;

    /**
     * 提交订单。
     * 当前是数据库直连版下单流程：
     * 1. 校验参数
     * 2. 查询票档
     * 3. 扣减库存
     * 4. 创建订单并落库
     * 5. 返回下单结果
     */
    @Override
    @Transactional
    public OrderSubmitVO submit(TicketOrderSubmitDTO ticketOrderSubmitDTO) {
        if (ticketOrderSubmitDTO.getTicketCount() == null || ticketOrderSubmitDTO.getTicketCount() <= 0) {
            throw new BaseException("购票数量必须大于 0");
        }

        TicketTier ticketTier = ticketTierMapper.getById(ticketOrderSubmitDTO.getTicketTierId());
        if (ticketTier == null) {
            throw new BaseException("票档不存在");
        }

        int rows = ticketTierMapper.deductStock(ticketTier.getId(), ticketOrderSubmitDTO.getTicketCount());
        if (rows == 0) {
            throw new StockNotEnoughException("库存不足");
        }

        BigDecimal amount = ticketTier.getPrice()
                .multiply(BigDecimal.valueOf(ticketOrderSubmitDTO.getTicketCount()));

        String orderNo = String.valueOf(System.currentTimeMillis()); // 暂时方案

        LocalDateTime now = LocalDateTime.now();

        TicketOrder ticketOrder = TicketOrder.builder()
                .orderNo(orderNo)
                .userId(ticketOrderSubmitDTO.getUserId())
                .eventId(ticketOrderSubmitDTO.getEventId())
                .ticketTierId(ticketOrderSubmitDTO.getTicketTierId())
                .ticketCount(ticketOrderSubmitDTO.getTicketCount())
                .amount(amount)
                .status(OrderStateEnum.CONFIRMED)
                .createTime(now)
                .updateTime(now)
                .build();

        ticketOrderMapper.insert(ticketOrder);

        return OrderSubmitVO.builder()
                .orderNo(orderNo)
                .orderStatus(OrderStateEnum.CONFIRMED.name())
                .amount(amount)
                .build();
    }
}
