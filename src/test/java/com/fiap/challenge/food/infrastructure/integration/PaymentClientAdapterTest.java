package com.fiap.challenge.food.infrastructure.integration;

import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.payment.PaymentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentClientAdapterTest {

    private PaymentClientAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new PaymentClientAdapter();
    }

    @Test
    @DisplayName("Should create payment with PENDING status, PIX type, transactionId and qrCode")
    void createReturnsPaymentWithExpectedFields() {
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("100.0"));

        Payment result = adapter.create(payment);

        assertNotNull(result.getTransactionId());
        assertNotNull(result.getQrCode());
        assertEquals(PaymentStatus.PENDING, result.getStatus());
        assertEquals(PaymentType.PIX, result.getType());
        assertEquals(new BigDecimal("100.0"), result.getAmount());
    }

    @Test
    @DisplayName("Should update payment status to APPROVED")
    void updateChangesPaymentStatus() {
        Payment payment = new Payment();
        payment.setStatus(PaymentStatus.PENDING);

        Payment result = adapter.update(payment, PaymentStatus.APPROVED);

        assertEquals(PaymentStatus.APPROVED, result.getStatus());
    }

    @Test
    @DisplayName("Should handle update when payment is null")
    void updateWithNullPaymentThrowsException() {
        assertThrows(NullPointerException.class, () -> adapter.update(null, PaymentStatus.APPROVED));
    }

    @Test
    @DisplayName("Should handle create when payment is null")
    void createWithNullPaymentThrowsException() {
        assertThrows(NullPointerException.class, () -> adapter.create(null));
    }
}
