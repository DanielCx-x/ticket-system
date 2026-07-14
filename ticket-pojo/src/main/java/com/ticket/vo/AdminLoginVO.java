package com.ticket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员登录响应 VO。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginVO {

    // 管理员 ID
    private Long id;

    // 管理员用户名
    private String username;

    // 管理员姓名
    private String name;

    // 登录成功后签发的 JWT
    private String token;
}