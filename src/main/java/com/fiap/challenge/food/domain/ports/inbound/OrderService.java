package com.fiap.challenge.food.domain.ports.inbound;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.order.OrderStatus;

import java.util.List;

public interface OrderService {

    public PageResult<Order> getAllByStatus(List<OrderStatus> status, int page, int size);

    public void approvePayment(String transactionId);

    public void approvePayment(Long orderId);
}
