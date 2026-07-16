package com.ticket.controller.admin;

import com.ticket.result.Result;
import com.ticket.service.StockRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/admin/stocks")
@RequiredArgsConstructor
public class AdminStockController {

    private final StockRedisService stockRedisService;

    /**
     * 将 MySQL 中的票档库存初始化到 Redis。
     */
    @PostMapping("/init")
    public Result<Void> initStockToRedis() {
        stockRedisService.initStockToRedis();
        return Result.success();
    }

    /**
     * 测试 Redis Lua 原子扣减库存。
     */
    @PostMapping("/deduct-test")
    public Result<Long> deductStock(@RequestParam Long ticketTierId,
                                    @RequestParam Integer count) {
        return Result.success(stockRedisService.deductStock(ticketTierId, count));
    }

    /**
     * 测试 Redis Lua 回滚库存。
     */
    @PostMapping("/rollback-test")
    public Result<Long> rollbackStock(@RequestParam Long ticketTierId,
                                      @RequestParam Integer count) {
        return Result.success(stockRedisService.rollbackStock(ticketTierId, count));
    }
}