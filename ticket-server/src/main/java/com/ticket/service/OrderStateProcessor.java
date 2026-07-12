package com.ticket.service;

import com.ticket.enums.OrderStatusEnum;

public interface OrderStateProcessor {

    /**
     * 判断订单状态是否允许从 source 流转到 target。
     */
    boolean canTransit(OrderStatusEnum source, OrderStatusEnum target);
}