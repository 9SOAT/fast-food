package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.application.response.OrderItemView;

import java.math.BigDecimal;

public class OrderItemViewFixture {
    public static OrderItemView validItem() {
        return new OrderItemView("1", "X-Tudo", "SANDWICH", 2, new BigDecimal("20.00"), new BigDecimal("40.00"));
    }

}
