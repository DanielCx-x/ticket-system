package com.ticket.mapper;

import com.ticket.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    /**
     * 根据用户名查询管理员。
     */
    @Select("select id, username, password, name, create_time, update_time " +
            "from `admin` where username = #{username}")
    Admin getByUsername(String username);
}