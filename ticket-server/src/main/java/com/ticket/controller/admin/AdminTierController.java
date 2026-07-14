package com.ticket.controller.admin;

import com.ticket.result.Result;
import com.ticket.service.TicketTierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/tiers")
@RequiredArgsConstructor
public class AdminTierController {

    private final TicketTierService ticketTierService;

    /**
     * 管理端修改票档库存。
     */
    // PUT /admin/tiers/{tierId}/stock?totalStock=1000
    @PutMapping("/{tierId}/stock")
    public Result<Void> updateStock(@PathVariable Long tierId,
                                    @RequestParam Integer totalStock) {
        ticketTierService.updateStock(tierId, totalStock);
        return Result.success();
    }
}