package com.fiap.challenge.food.domain.model.cart;

public enum CartStatus {
    OPEN,
    CHECKED_OUT;

    public Boolean isOpen() {
        return this == OPEN;
    }
}
