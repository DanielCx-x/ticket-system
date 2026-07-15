package com.ticket.controller.admin;

import com.ticket.result.Result;
import com.ticket.service.StockRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}