package com.fiap.challenge.food.domain.ports.inbound;

import com.fiap.challenge.food.domain.model.order.Order;

public interface CheckoutService {

    public Order checkout(Long cartId);

}
