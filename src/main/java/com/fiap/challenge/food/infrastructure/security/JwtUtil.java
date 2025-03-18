package com.fiap.challenge.food.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    private static final String SECRET_KEY = "c66b463c-376f-452b-8a7e-b17491518828";

    private static SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static Long extractConsumerId(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

            Object userIdObj = claims.get("consumer_id");

            if (userIdObj instanceof String) {
                return Long.parseLong((String) userIdObj);
            } else if (userIdObj instanceof Number) {
                return ((Number) userIdObj).longValue();
            }

            return null;
        } catch (Exception e) {
            log.warn("Erro ao processar JWT: {}", e.getMessage());
            return null;
        }
    }

    public static Long extractConsumerIdFromToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return JwtUtil.extractConsumerId(token);
        }
        return null;
    }
}
