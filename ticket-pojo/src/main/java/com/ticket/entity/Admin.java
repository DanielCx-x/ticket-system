package com.ticket.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员实体类，对应 admin 表。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    // 管理员主键 ID
    private Long id;

    // 管理员用户名
    private String username;

    // 密码。当前阶段先明文存储，后续会升级为加密存储。
    private String password;

    // 管理员姓名
    private String name;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}