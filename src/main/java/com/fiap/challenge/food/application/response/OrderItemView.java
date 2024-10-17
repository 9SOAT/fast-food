package com.fiap.challenge.food.application.response;

import java.math.BigDecimal;

public record OrderItemView(
    Long productId,
    String productName,
    ProductCategoryView productCategory,
    Integer quantity,
    BigDecimal price,
    BigDecimal subtotal
) {
}
