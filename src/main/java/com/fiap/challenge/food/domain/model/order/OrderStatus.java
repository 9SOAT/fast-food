package com.fiap.challenge.food.domain.model.order;

import java.util.List;

public enum OrderStatus {
    CANCELLED,
    WAITING_PAYMENT,
    READY_FOR_PREPARATION,
    IN_PREPARATION,
    READY_FOR_PICKUP,
    FINISHED;

    public static List<OrderStatus> getInProgressStatuses() {
        return List.of(READY_FOR_PREPARATION, IN_PREPARATION, READY_FOR_PICKUP);
    }

    public boolean isWaitingPayment() {
        return this == WAITING_PAYMENT;
    }

    public boolean isInProgress() {
        return getInProgressStatuses().contains(this);
    }
}


