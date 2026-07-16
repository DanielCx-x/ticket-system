package com.ticket.service;

public interface StockRedisService {

    /**
     * 将 MySQL 中的票档库存初始化到 Redis。
     */
    void initStockToRedis();

    /**
     * 从 Redis 原子扣减库存。
     *
     * @return 1-扣减成功，0-库存不足，-1-库存未初始化
     */
    Long deductStock(Long ticketTierId, Integer count);

    /**
     * 回滚 Redis 库存。
     *
     * @return 1-回滚成功
     */
    Long rollbackStock(Long ticketTierId, Integer count);
}