package com.ticket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录响应 VO。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO {

    // 用户 ID
    private Long id;

    // 用户名
    private String username;

    // 用户昵称
    private String nickname;

    // 登录成功后签发的 JWT
    private String token;
}
