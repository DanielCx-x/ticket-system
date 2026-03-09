package com.ticket.service.impl;

import com.ticket.entity.TicketTier;
import com.ticket.mapper.TicketTierMapper;
import com.ticket.service.TicketTierService;
import com.ticket.vo.TicketTierVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketTierServiceImpl implements TicketTierService {

    private final TicketTierMapper ticketTierMapper;

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
}
