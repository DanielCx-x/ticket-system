package com.ticket.service.impl;

import com.ticket.context.BaseContext;
import com.ticket.dto.TicketOrderSubmitDTO;
import com.ticket.entity.TicketOrder;
import com.ticket.entity.TicketTier;
import com.ticket.enums.OrderStatusEnum;
import com.ticket.exception.BaseException;
import com.ticket.exception.StockNotEnoughException;
import com.ticket.mapper.TicketOrderMapper;
import com.ticket.mapper.TicketTierMapper;
import com.ticket.service.TicketOrderService;
import com.ticket.service.OrderStatusProcessor;
import com.ticket.vo.OrderSubmitVO;
import com.ticket.vo.OrderDetailVO;
import com.ticket.utils.NoGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketOrderServiceImpl implements TicketOrderService {

    private final TicketTierMapper ticketTierMapper;
    private final TicketOrderMapper ticketOrderMapper;
    private final OrderStatusProcessor orderStatusProcessor;

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
        Long currentUserId = getCurrentUserId();

        if (ticketOrderSubmitDTO.getTicketCount() == null || ticketOrderSubmitDTO.getTicketCount() <= 0) {
            throw new BaseException("购票数量必须大于 0");
        }

        TicketTier ticketTier = ticketTierMapper.getById(ticketOrderSubmitDTO.getTicketTierId());
        if (ticketTier == null) {
            throw new BaseException("票档不存在");
        }

        if (!ticketTier.getEventId().equals(ticketOrderSubmitDTO.getEventId())) {
            throw new BaseException("票档与活动信息不一致");
        }

        int rows = ticketTierMapper.deductStock(ticketTier.getId(), ticketOrderSubmitDTO.getTicketCount());
        if (rows == 0) {
            throw new StockNotEnoughException("库存不足");
        }

        BigDecimal amount = ticketTier.getPrice()
                .multiply(BigDecimal.valueOf(ticketOrderSubmitDTO.getTicketCount()));

        String orderNo = NoGenerator.generateOrderNo(currentUserId);

        LocalDateTime now = LocalDateTime.now();

        TicketOrder ticketOrder = TicketOrder.builder()
                .orderNo(orderNo)
                .userId(currentUserId)
                .eventId(ticketTier.getEventId())
                .ticketTierId(ticketOrderSubmitDTO.getTicketTierId())
                .ticketCount(ticketOrderSubmitDTO.getTicketCount())
                .amount(amount)
                .status(OrderStatusEnum.CONFIRMED)
                .createTime(now)
                .updateTime(now)
                .build();

        ticketOrderMapper.insert(ticketOrder);

        return OrderSubmitVO.builder()
                .orderNo(orderNo)
                .orderStatus(OrderStatusEnum.CONFIRMED.name())
                .amount(amount)
                .build();
    }

    @Override
    public OrderDetailVO getUserOrderDetail(String orderNo) {
        Long currentUserId = getCurrentUserId();
        TicketOrder ticketOrder = ticketOrderMapper.getByOrderNo(orderNo);
        if (ticketOrder == null) {
            throw new BaseException("订单不存在");
        }

        if (!ticketOrder.getUserId().equals(currentUserId)) {
            throw new BaseException("无权查看该订单");
        }

        return toOrderDetailVO(ticketOrder);
    }

    @Override
    public OrderDetailVO getAdminOrderDetail(String orderNo) {
        TicketOrder ticketOrder = ticketOrderMapper.getByOrderNo(orderNo);
        if (ticketOrder == null) {
            throw new BaseException("订单不存在");
        }

        return toOrderDetailVO(ticketOrder);
    }

    @Override
    @Transactional
    public void cancelByOrderNo(String orderNo){
        Long currentUserId = getCurrentUserId();

        TicketOrder ticketOrder = ticketOrderMapper.getByOrderNo(orderNo);
        if (ticketOrder == null) {
            throw new BaseException("订单不存在");
        }

        if (!ticketOrder.getUserId().equals(currentUserId)) {
            throw new BaseException("无权取消该订单");
        }

        if (!orderStatusProcessor.canTransit(ticketOrder.getStatus(), OrderStatusEnum.CANCELLED)) {
            throw new BaseException("当前订单状态不允许取消");
        }

        LocalDateTime now = LocalDateTime.now();

        int rows = ticketOrderMapper.updateStatus(
            orderNo,
            ticketOrder.getStatus(),
            OrderStatusEnum.CANCELLED,
            now
        );

        if (rows == 0) {
            throw new BaseException("订单状态已变化，请刷新后重试");
        }

        int stockRows = ticketTierMapper.increaseStock(
            ticketOrder.getTicketTierId(),
            ticketOrder.getTicketCount()
        );

        if (stockRows == 0) {
            throw new BaseException("库存恢复失败");
        }
    }

    @Override
    public List<OrderDetailVO> listCurrentUserOrders() {
        Long currentUserId = getCurrentUserId();
        
        List<TicketOrder> ticketOrders = ticketOrderMapper.listByUserId(currentUserId);
        
        List<OrderDetailVO> result = new ArrayList<>();
        for (TicketOrder ticketOrder : ticketOrders) {
            OrderDetailVO orderDetailVO = toOrderDetailVO(ticketOrder);
            result.add(orderDetailVO);
        }
        return result;
        //return ticketOrderMapper.listByUserId(currentUserId).stream()
            //.map(this::toOrderDetailVO)
            //.collect(Collectors.toList());
    }

    @Override
    public List<OrderDetailVO> listAllOrders() {
        List<TicketOrder> ticketOrders = ticketOrderMapper.listAll();

        List<OrderDetailVO> result = new ArrayList<>();
        for (TicketOrder ticketOrder : ticketOrders) {
            OrderDetailVO orderDetailVO = toOrderDetailVO(ticketOrder);
            result.add(orderDetailVO);
        }
        return result;
    }

    private Long getCurrentUserId() {
        Long currentUserId = BaseContext.getCurrentId();
        if (currentUserId == null) {
            throw new BaseException("用户未登录");
        }
        return currentUserId;
    }

    private OrderDetailVO toOrderDetailVO(TicketOrder ticketOrder) {
        return OrderDetailVO.builder()
            .orderNo(ticketOrder.getOrderNo())
            .userId(ticketOrder.getUserId())
            .eventId(ticketOrder.getEventId())
            .ticketTierId(ticketOrder.getTicketTierId())
            .ticketCount(ticketOrder.getTicketCount())
            .amount(ticketOrder.getAmount())
            .orderStatus(ticketOrder.getStatus().name())
            .createTime(ticketOrder.getCreateTime())
            .build();
    }
}
