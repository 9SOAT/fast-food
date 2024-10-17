package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.application.request.ConsumerMutation;
import com.fiap.challenge.food.application.response.ConsumerView;

public class ConsumerViewFixture {

    public static ConsumerView aConsumerView() {
        return new ConsumerView(null, "John Doe", "john.doe@fiap.com", "909.532.740-04");
    }

    public static ConsumerView aConsumerViewWithId() {
        return new ConsumerView(1L, "John Doe", "john.doe@fiap.com", "909.532.740-04");
    }

    public static ConsumerMutation aConsumerMutation() {
        return new ConsumerMutation("John Doe", "john.doe@fiap.com", "909.532.740-04");
    }

    public static ConsumerMutation aConsumerMutationWithNullName() {
        return new ConsumerMutation(null, "john.doe@fiap.com", "909.532.740-04");
    }

    public static ConsumerMutation aConsumerMutationWithEmptyName() {
        return new ConsumerMutation("", "john.doe@fiap.com", "909.532.740-04");
    }

    public static ConsumerMutation aConsumerMutationWithNullEmail() {
        return new ConsumerMutation("John Doe", null, "909.532.740-04");
    }

    public static ConsumerMutation aConsumerMutationWithEmptyEmail() {
        return new ConsumerMutation("John Doe", "", "909.532.740-04");
    }

    public static ConsumerMutation aConsumerMutationWithNullCpf() {
        return new ConsumerMutation("John Doe", "john.doe@fiap.com", null);
    }

    public static ConsumerMutation aConsumerMutationWithEmptyCpf() {
        return new ConsumerMutation("John Doe", "john.doe@fiap.com", "");
    }

    public static ConsumerMutation aConsumerMutationWithInvalidCpf() {
        return new ConsumerMutation("John Doe", "john.doe@fiap.com", "11111111111");
    }

}
