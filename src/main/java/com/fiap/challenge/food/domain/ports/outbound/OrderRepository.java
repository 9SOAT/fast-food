package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    public Order save(Order order);

    PageResult<Order> findAllByStatusIn(List<OrderStatus> inProgressStatuses, int page, int size);

    Optional<Order> findByPaymentTransactionId(String transactionId);

    Optional<Order> findById(Long id);
}
