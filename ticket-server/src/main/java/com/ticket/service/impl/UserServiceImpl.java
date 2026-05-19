package com.ticket.service.impl;

import com.ticket.dto.UserLoginDTO;
import com.ticket.entity.User;
import com.ticket.exception.BaseException;
import com.ticket.mapper.UserMapper;
import com.ticket.properties.JwtProperties;
import com.ticket.service.UserService;
import com.ticket.utils.JwtUtil;
import com.ticket.vo.UserLoginVO;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        if (userLoginDTO == null
                || !StringUtils.hasText(userLoginDTO.getUsername())
                || !StringUtils.hasText(userLoginDTO.getPassword())) {
            throw new BaseException("用户名或密码不能为空");
        }

        User user = userMapper.getByUsername(userLoginDTO.getUsername());
        if (user == null) {
            throw new BaseException("用户不存在");
        }

        if (!user.getPassword().equals(userLoginDTO.getPassword())) {
            throw new BaseException("密码错误");
        }

        String token = JwtUtil.createJwt(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                Map.of("userId", user.getId(), "username", user.getUsername())
        );

        return UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .token(token)
                .build();
    }
}
