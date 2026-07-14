package com.ticket.interceptor;

import com.ticket.context.BaseContext;
import com.ticket.exception.BaseException;
import com.ticket.properties.JwtProperties;
import com.ticket.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 管理端 JWT 拦截器。
 *
 * 1. 从请求头中读取 admin token
 * 2. 校验 token 是否有效
 * 3. 从 token 中解析 adminId
 * 4. 把 adminId 放入 BaseContext，供后续管理端 Service 使用
 */
@Component
@RequiredArgsConstructor
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        if (!StringUtils.hasText(token)) {
            throw new BaseException("管理员未登录");
        }

        try {
            Claims claims = JwtUtil.parseJwt(jwtProperties.getAdminSecretKey(), token);
            Long adminId = Long.valueOf(claims.get("adminId").toString());

            BaseContext.setCurrentId(adminId);
            return true;
        } catch (Exception e) {
            throw new BaseException("管理员登录已失效，请重新登录");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        BaseContext.removeCurrentId();
    }
}