package com.ticket.mapper;

import com.ticket.entity.Event;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EventMapper {

    /**
     * 根据活动ID查询活动信息
     */
    @Select("select id, name, venue, show_time, status from event where id = #{id}")
    Event getById(Long id);

    /**
     * 查询所有活动
     */
    @Select("select id, name, venue, show_time, status from event order by show_time asc")
    List<Event> list();

    /**
     * 新增活动
     */
    @Insert("insert into event " +
            "(name, venue, show_time, status) " +
            "values " +
            "(#{name}, #{venue}, #{showTime}, #{status})")
    void insert(Event event);

    /**
     * 修改活动状态
     */
    @Update("update event set status = #{status} where id = #{id}")
    int updateStatus(
        @Param("id") Long id, 
        @Param("status") Integer status
    );
}
