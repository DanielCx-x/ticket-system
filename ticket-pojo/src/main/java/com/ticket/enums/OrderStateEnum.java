package com.ticket.enums;

/**
 * 订单状态枚举。
 * 后续状态机会围绕这些状态做流转控制。
 */
public enum OrderStateEnum {
    INIT, // 初始状态，订单对象刚创建但还未进入正式处理流程
    QUEUED, // 已进入异步处理队列
    CONFIRMED, // 已确认，通常表示真实落库与库存扣减成功
    PAID, // 已支付
    FAILED, // 处理失败
    CANCELLED // 已取消
}
