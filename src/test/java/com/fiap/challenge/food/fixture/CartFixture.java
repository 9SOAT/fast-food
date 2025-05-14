package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.model.cart.CartItem;
import com.fiap.challenge.food.domain.model.cart.CartStatus;
import com.fiap.challenge.food.domain.model.product.ProductCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartFixture {

    public static Cart aEmptyCart() {
        return new Cart(1L, "08444331015", new ArrayList<>(), CartStatus.OPEN, LocalDateTime.now());
    }

    public static Cart aCartWithASandwich() {
        Cart cart = aEmptyCart();
        cart.setItems(List.of(new CartItem(null, 1L, "X-TUDO", ProductCategory.SANDWICH, new BigDecimal("20.00"), 1 , LocalDateTime.now())));
        return cart;
    }

}
