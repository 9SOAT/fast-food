package com.fiap.challenge.food.domain.model.cart;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartItemTest {

    @Test
    void cartItemEntityTotalPriceCalculation() {
        CartItem cartItem = new CartItem(null, "1", "X-TUDO", "SANDWICH", new BigDecimal("7.97"), 7, LocalDateTime.now());
        assertEquals(BigDecimal.valueOf(55.79), cartItem.getSubTotal());
    }

    @Test
    void cartItemIncreaseQuantity() {
        CartItem cartItem = new CartItem(null, "1", "X-TUDO", "SANDWICH", new BigDecimal("7.97"), 7, LocalDateTime.now());
        cartItem.increaseQuantity(3);
        assertEquals(10, cartItem.getQuantity());
    }

    @Test
    void cartItemDecreaseQuantity() {
        CartItem cartItem = new CartItem(null, "1", "X-TUDO", "SANDWICH", new BigDecimal("7.97"), 7, LocalDateTime.now());
        cartItem.decreaseQuantity(3);
        assertEquals(4, cartItem.getQuantity());
    }

    @Test
    void cartItemDecreaseQuantityWithZeroValue() {
        CartItem cartItem = new CartItem(null, "1", "X-TUDO", "SANDWICH", new BigDecimal("7.97"), 7, LocalDateTime.now());
        cartItem.decreaseQuantity(0);
        assertEquals(7, cartItem.getQuantity());
    }
}
