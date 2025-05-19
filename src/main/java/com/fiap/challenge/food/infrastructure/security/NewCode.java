package com.fiap.challenge.food.infrastructure.security;

import java.util.UUID;

public class NewCode {

    public String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}
