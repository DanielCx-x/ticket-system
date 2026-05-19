package com.ticket.mapper;

import com.ticket.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户。
     */
    @Select("select id, username, password, nickname, phone, create_time, update_time " +
            "from `user` where username = #{username}")
    User getByUsername(String username);
}
