package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.application.response.CartItemView;
import com.fiap.challenge.food.application.response.CartView;

import java.math.BigDecimal;
import java.util.List;

public class CartViewFixture {
    public static CartView aEmptyCartView() {
        return new CartView(1L, "08444331015", List.of(), BigDecimal.ZERO);
    }

    public static CartView aCartViewWithSandwich() {
        return new CartView(1L, "08444331015", List.of(new CartItemView("1", "X-TUDO",
            new BigDecimal("20.00"), 2, new BigDecimal("40.00"), "SANDWICH")), new BigDecimal("40.00"));
    }
}
