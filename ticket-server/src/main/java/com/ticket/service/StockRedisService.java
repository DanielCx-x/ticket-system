package com.ticket.service;

public interface StockRedisService {

    /**
     * 将 MySQL 中的票档库存初始化到 Redis。
     */
    void initStockToRedis();
}