package com.fiap.challenge.food.application.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemMutation(
    @NotNull Long productId,
    @NotNull @Min(1) Integer quantity
) {

}
