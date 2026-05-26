package com.ticket.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JwtUtil {

    private JwtUtil() {
    }

    // 创建 JWT
    public static String createJwt(String secretKey, long ttlMillis, Map<String, Object> claims) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + ttlMillis);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(toSecretKey(secretKey), Jwts.SIG.HS256)
                .compact();
    }

    // 解析 JWT
    public static Claims parseJwt(String secretKey, String token) {
        return Jwts.parser()
                .verifyWith(toSecretKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 把配置文件中的字符串密钥转换成 HMAC-SHA256 能使用的 SecretKey
    private static SecretKey toSecretKey(String secretKey) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(secretKey.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(digest, "HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm is unavailable", e);
        }
    }
}
