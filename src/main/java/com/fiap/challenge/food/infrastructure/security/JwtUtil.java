package com.fiap.challenge.food.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtUtil {

    private final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret-key}")
    private String secretKey;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String extractConsumerId(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

            Object userIdObj = claims.get("cpf");
            return (String) userIdObj;
        } catch (Exception e) {
            log.warn("Erro ao processar JWT: {}", e.getMessage());
            return null;
        }
    }

    public String extractConsumerIdFromToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return extractConsumerId(token);
        }
        return null;
    }
}
