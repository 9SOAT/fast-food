package com.fiap.challenge.food.application.request;

import jakarta.validation.constraints.NotNull;

public record CheckoutMutation(
    @NotNull Long cartId
) {
}
