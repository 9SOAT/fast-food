package com.fiap.challenge.food.infrastructure.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CartEntityTest {

    @Test
    void cartEntityRemoveItem() {
        CartEntity cart = new CartEntity();
        CartItemEntity item = new CartItemEntity();
        cart.setItems(List.of(item));
        assertNotNull(item.getCart());
    }
}
