package com.ticket.mapper;

import com.ticket.entity.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventMapper {

    /**
     * 根据活动ID查询活动信息
     */
    @Select("select id, name, venue, show_time, status from event where id = #{id}")
    Event getById(Long id);
}
