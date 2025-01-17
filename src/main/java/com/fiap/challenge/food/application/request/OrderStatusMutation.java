package com.fiap.challenge.food.application.request;

import com.fiap.challenge.food.domain.model.order.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusMutation(
    @NotNull OrderStatus status
) {
}

