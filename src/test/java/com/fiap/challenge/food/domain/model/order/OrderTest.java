package com.fiap.challenge.food.domain.model.order;

import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.fixture.OrderFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {


    @Test
    void shouldApproveOrderPayment() {
        Order order = OrderFixture.validOrder();
        order.approvePayment();

        assertEquals(OrderStatus.READY_FOR_PREPARATION, order.getStatus());
        assertEquals(PaymentStatus.APPROVED, order.getPayment().getStatus());
    }

    @Test
    void shouldReturnTrueWhenOrderIsWaitingPaymentStatus() {
        Order order = OrderFixture.validOrder();
        assertTrue(order.isWaitingPaymentStatus());
    }

    @Test
    void shouldReturnFalseWhenOrderIsWaitingPaymentStatus() {
        Order order = OrderFixture.validOrder();
        order.approvePayment();
        assertFalse(order.isWaitingPaymentStatus());
    }

    @Test
    void shouldReturnZeroWhenOrderIsNotInProgress() {
        Order order = OrderFixture.validOrder();
        assertEquals(0, order.getWaitingMinutes());
    }

    @Test
    void shouldReturnMinutesWhenOrderIsInProgress() {
        Order order = OrderFixture.validOrder();
        order.approvePayment();
        order.getPayment().setApprovedAt(LocalDateTime.now().minusMinutes(5));
        assertEquals(5, order.getWaitingMinutes());
    }

    @Test
    void shouldReturnZeroWhenPaymentIsNotApproved() {
        Order order = OrderFixture.validOrder();
        order.approvePayment();
        order.getPayment().setStatus(PaymentStatus.PENDING);
        assertEquals(0, order.getWaitingMinutes());
    }

    @Test
    void shouldReturnZeroWhenPaymentApprovedAtIsNull() {
        Order order = OrderFixture.validOrder();
        order.approvePayment();
        order.getPayment().setApprovedAt(null);
        assertEquals(0, order.getWaitingMinutes());
    }

    @Test
    void shouldReturnZeroWhenPaymentApprovedAtIsNullAndOrderIsNotInProgress() {
        Order order = OrderFixture.validOrder();
        order.getPayment().setApprovedAt(null);
        assertEquals(0, order.getWaitingMinutes());
    }


}
