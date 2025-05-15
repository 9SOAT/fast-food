package com.fiap.challenge.food.infrastructure.integration;

import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.infrastructure.entity.OrderEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;


public interface FastFoodOrderClient {

    @PostExchange(value = "/orders/create/")
    OrderEntity saveOrder(@RequestBody Order order);

    @PostExchange(value = "/orders/{id}/payment/approve")
    void approvePayment(@PathVariable String transactionId);

    @PostExchange(value = "/orders/{id}/payment/reject")
    void rejectPayment(@PathVariable String transactionId);

}
