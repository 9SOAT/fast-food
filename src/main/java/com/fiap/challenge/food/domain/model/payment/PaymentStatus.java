package com.fiap.challenge.food.domain.model.payment;

public enum PaymentStatus {
    PENDING,
    APPROVED,
    REJECTED;

    public boolean isPending() {
        return this == PENDING;
    }
}
