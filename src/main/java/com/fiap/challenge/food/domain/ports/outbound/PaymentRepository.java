package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.payment.Payment;

import java.util.Optional;

public interface PaymentRepository {

    Optional<Payment> findById(Long id);
    Optional<Payment> findByTransactionId(String transactionId);
    Payment save(Payment payment);

}
