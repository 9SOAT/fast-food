package com.fiap.challenge.food.domain.ports.inbound;

import com.fiap.challenge.food.domain.model.cart.Cart;

public interface CartService {
    public Cart create(String consumerId);
    public Cart getById(Long id);
    public Cart addItem(Long cartId, String productId, int quantity);
}
