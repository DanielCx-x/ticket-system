package com.ticket.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类，对应 user 表。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // 用户主键 ID
    private Long id;

    // 用户名，用于登录
    private String username;

    // 密码。当前阶段先明文存储，后续会升级为加密存储。
    private String password;

    // 用户昵称
    private String nickname;

    // 手机号
    private String phone;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
