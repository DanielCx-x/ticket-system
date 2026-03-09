package com.ticket.mapper;

import com.ticket.entity.TicketOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketOrderMapper {

    /**
     * 新增订单
     */
    @Insert("insert into ticket_order " +
            "(order_no, user_id, event_id, ticket_tier_id, ticket_count, amount, status, create_time, update_time) " +
            "values " +
            "(#{orderNo}, #{userId}, #{eventId}, #{ticketTierId}, #{ticketCount}, #{amount}, #{status}, #{createTime}, #{updateTime})")
    void insert(TicketOrder ticketOrder);
}
