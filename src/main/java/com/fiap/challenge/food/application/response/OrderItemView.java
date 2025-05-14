package com.fiap.challenge.food.application.response;

import java.math.BigDecimal;

public record OrderItemView(
    String productId,
    String productName,
    String productCategory,
    Integer quantity,
    BigDecimal price,
    BigDecimal subtotal
) {
}
