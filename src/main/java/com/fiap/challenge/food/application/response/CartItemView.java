package com.fiap.challenge.food.application.response;

import java.math.BigDecimal;


public record CartItemView(
    String productId,
    String productName,
    BigDecimal price,
    Integer quantity,
    BigDecimal subTotal,
    String category
) {
}
