package com.ticket.service.impl;

import com.ticket.entity.TicketTier;
import com.ticket.mapper.TicketTierMapper;
import com.ticket.service.StockRedisService;
import java.util.List;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
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

    @Override
    public Long deductStock(Long ticketTierId, Integer count) {
        String key = STOCK_KEY_PREFIX + ticketTierId;

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/deduct_stock.lua"));
        script.setResultType(Long.class);

        return stringRedisTemplate.execute(
                script,
                Collections.singletonList(key),
                String.valueOf(count)
        );
    }

    @Override
    public Long rollbackStock(Long ticketTierId, Integer count) {
        String key = STOCK_KEY_PREFIX + ticketTierId;

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/rollback_stock.lua"));
        script.setResultType(Long.class);

        return stringRedisTemplate.execute(
                script,
                Collections.singletonList(key),
                String.valueOf(count)
        );
    }
}