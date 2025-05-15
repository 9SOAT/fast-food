package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.order.Order;

public interface OrderRepository {
    Order save(Order order);

    void approvePayment(String transactionId);

    void rejectPayment(String transactionId);
}
