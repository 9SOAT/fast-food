package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.domain.model.consumer.Consumer;

public class ConsumerFixture {

    public static Consumer aConsumer() {
        return new Consumer(null, "John Doe", "john.doe@fiap.com", "12345678901");
    }

    public static Consumer aConsumerWithId() {
        return new Consumer(1L, "John Doe", "john.doe@fiap.com", "12345678901");
    }
}
