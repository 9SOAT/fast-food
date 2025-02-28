package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.application.response.CartItemView;
import com.fiap.challenge.food.application.response.CartView;
import com.fiap.challenge.food.application.response.ProductCategoryView;

import java.math.BigDecimal;
import java.util.List;

public class CartViewFixture {
    public static CartView aEmptyCartView() {
        return new CartView(1L, 1L, List.of(), BigDecimal.ZERO);
    }

    public static CartView aCartViewWithSandwich() {
        return new CartView(1L, 1L, List.of(new CartItemView(1L, "X-TUDO",
            new BigDecimal("20.00"), 2, new BigDecimal("40.00"), ProductCategoryView.SANDWICH)), new BigDecimal("40.00"));
    }
}
