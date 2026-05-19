package com.ticket.service;

import com.ticket.dto.UserLoginDTO;
import com.ticket.vo.UserLoginVO;

public interface UserService {

    /**
     * 用户登录。
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);
}
