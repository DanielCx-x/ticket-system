package com.ticket.mapper;

import com.ticket.entity.PaymentRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaymentRecordMapper {
    /**
     * 新增支付记录
     */
    @Insert("insert into payment_record " +
            "(payment_no, order_no, user_id, amount, status, pay_time, create_time, update_time) " +
            "values " +
            "(#{paymentNo}, #{orderNo}, #{userId}, #{amount}, #{status}, #{payTime}, #{createTime}, #{updateTime})")
    void insert(PaymentRecord paymentRecord);

    /**
     * 根据订单号查询支付记录
     */
    @Select("select id, payment_no, order_no, user_id, amount, status, pay_time, create_time, update_time " +
            "from payment_record where order_no = #{orderNo}")
    PaymentRecord getByOrderNo(String orderNo);
    
}
