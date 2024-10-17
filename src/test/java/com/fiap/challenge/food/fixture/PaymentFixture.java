package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.payment.PaymentType;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentFixture {

    public static Payment aPixPayment() {
        return new Payment(1L, "123456", PaymentType.PIX, BigDecimal.valueOf(100.0), UUID.randomUUID().toString(), PaymentStatus.PENDING, null);
    }
}
