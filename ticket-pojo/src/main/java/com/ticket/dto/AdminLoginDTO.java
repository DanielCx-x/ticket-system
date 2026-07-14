package com.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员登录请求 DTO。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginDTO {

    // 管理员用户名
    private String username;

    // 密码
    private String password;
}