package com.ticket.interceptor;

import com.ticket.context.BaseContext;
import com.ticket.exception.BaseException;
import com.ticket.properties.JwtProperties;
import com.ticket.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户端 JWT 拦截器。
 *
 * 1. 从请求头中读取 token
 * 2. 校验 token 是否有效
 * 3. 从 token 中解析 userId
 * 4. 把 userId 放入 BaseContext，供后续 Service 使用
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, 
                            HttpServletResponse response, 
                            Object handler){
        String token = request.getHeader(jwtProperties.getUserTokenName());

        if (!StringUtils.hasText(token)) {
            throw new BaseException("用户未登录");
        }

        try {
            Claims claims = JwtUtil.parseJwt(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get("userId").toString());

            BaseContext.setCurrentId(userId);
            return true;
        } catch (Exception e) {
            throw new BaseException("用户登录已失效，请重新登录");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BaseContext.removeCurrentId();
    }
}
