package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.payment.Payment;


public interface PaymentClient {
    public Payment create(Payment payment);
}
