package com.fiap.challenge.food.infrastructure.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewCodeTest {

    @Test
    void testGenerateRandomString() {
        NewCode newCode = new NewCode();
        String randomString = newCode.generateRandomString();

        // Check if the generated string is not null or empty
        assertNotNull(randomString);
        assertFalse(randomString.isEmpty());

        // Check if the generated string is a valid UUID format
        assertTrue(randomString.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));
    }

}
