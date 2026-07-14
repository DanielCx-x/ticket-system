package com.ticket.service.impl;

import com.ticket.dto.AdminLoginDTO;
import com.ticket.entity.Admin;
import com.ticket.exception.BaseException;
import com.ticket.mapper.AdminMapper;
import com.ticket.properties.JwtProperties;
import com.ticket.service.AdminService;
import com.ticket.utils.JwtUtil;
import com.ticket.vo.AdminLoginVO;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final JwtProperties jwtProperties;

    @Override
    public AdminLoginVO login(AdminLoginDTO adminLoginDTO) {
        if (adminLoginDTO == null
                || !StringUtils.hasText(adminLoginDTO.getUsername())
                || !StringUtils.hasText(adminLoginDTO.getPassword())) {
            throw new BaseException("管理员用户名或密码不能为空");
        }

        Admin admin = adminMapper.getByUsername(adminLoginDTO.getUsername());
        if (admin == null) {
            throw new BaseException("管理员不存在");
        }

        if (!admin.getPassword().equals(adminLoginDTO.getPassword())) {
            throw new BaseException("密码错误");
        }

        String token = JwtUtil.createJwt(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                Map.of("adminId", admin.getId(), "username", admin.getUsername())
        );

        return AdminLoginVO.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .name(admin.getName())
                .token(token)
                .build();
    }
}