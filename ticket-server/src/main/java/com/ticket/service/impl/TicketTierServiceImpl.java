package com.ticket.service.impl;

import com.ticket.entity.TicketTier;
import com.ticket.mapper.TicketTierMapper;
import com.ticket.service.TicketTierService;
import com.ticket.vo.TicketTierVO;
import com.ticket.dto.TicketTierCreateDTO;
import com.ticket.entity.Event;
import com.ticket.exception.BaseException;
import com.ticket.exception.EventNotFoundException;
import com.ticket.mapper.EventMapper;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TicketTierServiceImpl implements TicketTierService {

    private final TicketTierMapper ticketTierMapper;
    private final EventMapper eventMapper;

    @Override
    public List<TicketTierVO> listByEventId(Long eventId) {
        List<TicketTier> ticketTierList = ticketTierMapper.listByEventId(eventId);

        return ticketTierList.stream()
                .map(ticketTier -> TicketTierVO.builder()
                        .id(ticketTier.getId())
                        .eventId(ticketTier.getEventId())
                        .tierName(ticketTier.getTierName())
                        .price(ticketTier.getPrice())
                        .availableStock(ticketTier.getAvailableStock())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void create(Long eventId, TicketTierCreateDTO ticketTierCreateDTO) {
        if (eventId == null) {
            throw new BaseException("活动ID不能为空");
        }

        Event event = eventMapper.getById(eventId);
        if (event == null) {
            throw new EventNotFoundException("活动不存在");
        }

        if (ticketTierCreateDTO == null
                || !StringUtils.hasText(ticketTierCreateDTO.getTierName())
                || ticketTierCreateDTO.getPrice() == null
                || ticketTierCreateDTO.getTotalStock() == null) {
            throw new BaseException("票档信息不能为空");
        }

        if (ticketTierCreateDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException("票价必须大于 0");
        }

        if (ticketTierCreateDTO.getTotalStock() < 0) {
            throw new BaseException("库存不能小于 0");
        }

        TicketTier ticketTier = TicketTier.builder()
                .eventId(eventId)
                .tierName(ticketTierCreateDTO.getTierName())
                .price(ticketTierCreateDTO.getPrice())
                .totalStock(ticketTierCreateDTO.getTotalStock())
                .availableStock(ticketTierCreateDTO.getTotalStock())
                .version(0)
                .build();

        ticketTierMapper.insert(ticketTier);
    }

    @Override
    public void updateStock(Long tierId, Integer totalStock) {
        if (tierId == null) {
            throw new BaseException("票档ID不能为空");
        }

        if (totalStock == null || totalStock < 0) {
            throw new BaseException("库存不能小于 0");
        }

        int rows = ticketTierMapper.updateStock(tierId, totalStock);
        if (rows == 0) {
            throw new BaseException("票档不存在");
        }
    }
}
