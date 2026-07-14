package com.ticket.service;

import com.ticket.dto.AdminLoginDTO;
import com.ticket.vo.AdminLoginVO;

public interface AdminService {

    /**
     * 管理员登录。
     */
    AdminLoginVO login(AdminLoginDTO adminLoginDTO);
}