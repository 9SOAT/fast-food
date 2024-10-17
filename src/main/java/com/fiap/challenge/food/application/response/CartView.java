package com.fiap.challenge.food.application.response;

import java.math.BigDecimal;
import java.util.List;


public record CartView(
    Long id,
    Long consumerId,
    List<CartItemView>items,
    BigDecimal total
) {
}
