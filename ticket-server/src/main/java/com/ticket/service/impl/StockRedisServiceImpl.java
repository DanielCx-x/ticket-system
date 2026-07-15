package com.ticket.service.impl;

import com.ticket.entity.TicketTier;
import com.ticket.mapper.TicketTierMapper;
import com.ticket.service.StockRedisService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockRedisServiceImpl implements StockRedisService {

    private static final String STOCK_KEY_PREFIX = "ticket:stock:";

    private final TicketTierMapper ticketTierMapper;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void initStockToRedis() {
        List<TicketTier> ticketTiers = ticketTierMapper.listAll();

        for (TicketTier ticketTier : ticketTiers) {
            String key = STOCK_KEY_PREFIX + ticketTier.getId();
            String value = String.valueOf(ticketTier.getAvailableStock());

            stringRedisTemplate.opsForValue().set(key, value);
        }
    }
}