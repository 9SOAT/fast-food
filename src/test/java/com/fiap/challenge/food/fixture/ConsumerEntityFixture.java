package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.infrastructure.entity.ConsumerEntity;

public class ConsumerEntityFixture {


    public static ConsumerEntity aConsumerEntity() {
        return new ConsumerEntity(null, "John Doe", "john.doe@fiap.com", "12345678901");
    }

    public static ConsumerEntity aValidConsumerEntityWithId() {
        return new ConsumerEntity(1L, "John Doe", "john.doe@fiap.com", "12345678901");
    }
}
