package com.fiap.challenge.food.domain.model.order;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void shouldReturnTrueWhenOrderStatusIsWaitingPayment() {
        assertTrue(OrderStatus.WAITING_PAYMENT.isWaitingPayment());
    }

    @Test
    void shouldReturnFalseWhenOrderStatusIsNotWaitingPayment() {
        assertFalse(OrderStatus.READY_FOR_PREPARATION.isWaitingPayment());
    }

    @Test
    void shouldReturnTrueWhenOrderStatusIsInProgress() {
        assertTrue(OrderStatus.READY_FOR_PREPARATION.isInProgress());
        assertTrue(OrderStatus.IN_PREPARATION.isInProgress());
        assertTrue(OrderStatus.READY_FOR_PICKUP.isInProgress());
    }

    @Test
    void shouldReturnFalseWhenOrderStatusIsNotInProgress() {
        assertFalse(OrderStatus.WAITING_PAYMENT.isInProgress());
        assertFalse(OrderStatus.FINISHED.isInProgress());
    }

    @Test
    void shouldReturnInProgressStatuses() {
        List<OrderStatus> inProgressStatuses = OrderStatus.getInProgressStatuses();
        assertEquals(3, inProgressStatuses.size());
        assertTrue(inProgressStatuses.contains(OrderStatus.READY_FOR_PREPARATION));
        assertTrue(inProgressStatuses.contains(OrderStatus.IN_PREPARATION));
        assertTrue(inProgressStatuses.contains(OrderStatus.READY_FOR_PICKUP));
    }
}
