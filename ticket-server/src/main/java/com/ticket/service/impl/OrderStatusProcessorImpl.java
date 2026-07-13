package com.ticket.service.impl;

import com.ticket.enums.OrderStatusEnum;
import com.ticket.service.OrderStatusProcessor;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusProcessorImpl implements OrderStatusProcessor {

    private final Map<OrderStatusEnum, Set<OrderStatusEnum>> allowedTransitions =
            new EnumMap<>(OrderStatusEnum.class);

    /**
    * 当前允许的订单状态流转规则：
    *
    * INIT      -> QUEUED / CONFIRMED / FAILED
    * QUEUED    -> CONFIRMED / FAILED
    * CONFIRMED -> PAID / CANCELLED
    * PAID      -> 不允许继续流转
    * FAILED    -> 不允许继续流转
    * CANCELLED -> 不允许继续流转
    */
    public OrderStatusProcessorImpl() {
        allowedTransitions.put(OrderStatusEnum.INIT,
                EnumSet.of(OrderStatusEnum.QUEUED, OrderStatusEnum.CONFIRMED, OrderStatusEnum.FAILED));

        allowedTransitions.put(OrderStatusEnum.QUEUED,
                EnumSet.of(OrderStatusEnum.CONFIRMED, OrderStatusEnum.FAILED));

        allowedTransitions.put(OrderStatusEnum.CONFIRMED,
                EnumSet.of(OrderStatusEnum.PAID, OrderStatusEnum.CANCELLED));

        allowedTransitions.put(OrderStatusEnum.PAID,
                EnumSet.noneOf(OrderStatusEnum.class));

        allowedTransitions.put(OrderStatusEnum.FAILED,
                EnumSet.noneOf(OrderStatusEnum.class));

        allowedTransitions.put(OrderStatusEnum.CANCELLED,
                EnumSet.noneOf(OrderStatusEnum.class));
    }

    @Override
    public boolean canTransit(OrderStatusEnum source, OrderStatusEnum target) {
        if (source == null || target == null) {
            return false;
        }

        Set<OrderStatusEnum> targets = allowedTransitions.get(source);
        return targets != null && targets.contains(target);
    }
}