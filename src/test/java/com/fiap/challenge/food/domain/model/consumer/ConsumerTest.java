package com.fiap.challenge.food.domain.model.consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsumerTest {

    @Test
    void shouldRemoveCpfMask() {
        Consumer consumer = new Consumer();
        consumer.setCpf("123.456.789-01");

        assertEquals("12345678901", consumer.getCpf());

    }
}
