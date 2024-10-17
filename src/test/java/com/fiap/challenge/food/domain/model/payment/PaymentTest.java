package com.fiap.challenge.food.domain.model.payment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void shouldApprovePayment() {
        Payment payment = new Payment();
        payment.approve();
        assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        assertNotNull(payment.getApprovedAt());
    }

    @Test
    void shouldReturnTrueWhenPaymentStatusIsPending() {
        Payment payment = new Payment();
        payment.setStatus(PaymentStatus.PENDING);
        assertTrue(payment.isPendingStatus());
    }

    @Test
    void shouldReturnFalseWhenPaymentStatusIsNotPending() {
        Payment payment = new Payment();
        payment.setStatus(PaymentStatus.APPROVED);
        assertFalse(payment.isPendingStatus());
    }
}
