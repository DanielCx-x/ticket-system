package com.ticket.mapper;

import com.ticket.entity.TicketOrder;
import com.ticket.enums.OrderStatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

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

    @Select("select id, order_no, user_id, event_id, ticket_tier_id, ticket_count, amount, status, create_time, update_time " +
            "from ticket_order where order_no = #{orderNo}")
    TicketOrder getByOrderNo(String orderNo);

    @Update("update ticket_order " +
            "set status = #{status}, update_time = #{updateTime} " +
            "where order_no = #{orderNo} and status = #{oldStatus}")
    int updateStatus(
        @Param("orderNo") String orderNo, 
        @Param("oldStatus") OrderStatusEnum oldStatus, 
        @Param("status") OrderStatusEnum status, 
        @Param("updateTime") LocalDateTime updateTime
	);

    @Select("select id, order_no, user_id, event_id, ticket_tier_id, ticket_count, amount, status, create_time, update_time " +
            "from ticket_order where user_id = #{userId} order by create_time desc")
    List<TicketOrder> listByUserId(Long userId);

	@Select("select id, order_no, user_id, event_id, ticket_tier_id, ticket_count, amount, status, create_time, update_time " +
    		"from ticket_order order by create_time desc")
	List<TicketOrder> listAll();
}
