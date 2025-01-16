package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.payment.Payment;

import java.util.Optional;

public interface PaymentRepository {

    public Optional<Payment> findById(Long id);
    Payment save(Payment payment);

}
