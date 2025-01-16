package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;


public interface PaymentClient {
    public Payment create(Payment payment);
    public Payment update(Payment payment, PaymentStatus paymentStatus);
}
