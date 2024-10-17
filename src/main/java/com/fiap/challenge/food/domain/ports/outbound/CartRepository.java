package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.cart.Cart;

import java.util.Optional;


public interface CartRepository {

    public Optional<Cart> findById(Long id);

    public Cart save(Cart cart);
}
