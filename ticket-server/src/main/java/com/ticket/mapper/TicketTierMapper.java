package com.ticket.mapper;

import com.ticket.entity.TicketTier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;


@Mapper
public interface TicketTierMapper {

    /**
     * 根据票档ID查询票档信息
     */
    @Select("select id, event_id, tier_name, price, total_stock, available_stock, version " +
            "from ticket_tier where id = #{id}")
    TicketTier getById(Long id);

    /**
     * 查询指定活动下的票档列表
     */
    @Select("select id, event_id, tier_name, price, total_stock, available_stock, version " +
            "from ticket_tier where event_id = #{eventId} order by price desc")
    List<TicketTier> listByEventId(Long eventId);

    /**
     * 扣减库存
     */
    @Update("update ticket_tier " +
            "set available_stock = available_stock - #{count} " +
            "where id = #{id} and available_stock >= #{count}")
    int deductStock(Long id, Integer count);
}
