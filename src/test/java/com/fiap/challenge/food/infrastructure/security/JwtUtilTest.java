package com.fiap.challenge.food.infrastructure.security;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // Chave de 32 bytes para HMAC-SHA-256
        ReflectionTestUtils.setField(jwtUtil, "secretKey", "12345678901234567890123456789012");
    }

    @Test
    @DisplayName("Deve retornar null se o header não começar com Bearer")
    void extractConsumerIdFromTokenReturnsNullIfHeaderDoesNotStartWithBearer() {
        String authHeader = "Token abc.def.ghi";
        String cpf = jwtUtil.extractConsumerIdFromToken(authHeader);
        assertNull(cpf);
    }

    @Test
    @DisplayName("Deve retornar null se o header for nulo")
    void extractConsumerIdFromTokenReturnsNullIfHeaderIsNull() {
        String cpf = jwtUtil.extractConsumerIdFromToken(null);
        assertNull(cpf);
    }

    @Test
    @DisplayName("Deve retornar null se o token for inválido")
    void extractConsumerIdFromTokenReturnsNullIfTokenIsInvalid() {
        String authHeader = "Bearer tokeninvalido";
        String cpf = jwtUtil.extractConsumerIdFromToken(authHeader);
        assertNull(cpf);
    }

    @Test
    @DisplayName("Deve retornar null se o claim cpf não existir no token")
    void extractConsumerIdFromTokenReturnsNullIfCpfClaimIsMissing() throws Exception {
        String token = Jwts.builder()
            .claim("outro", "valor")
            .compact();
        String authHeader = "Bearer " + token;
        String cpf = jwtUtil.extractConsumerIdFromToken(authHeader);
        assertNull(cpf);
    }
}
