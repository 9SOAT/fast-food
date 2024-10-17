package com.fiap.challenge.food.application.response;

import java.math.BigDecimal;


public record CartItemView(
    Long productId,
    String productName,
    BigDecimal price,
    Integer quantity,
    BigDecimal subTotal,
    ProductCategoryView category
) {
}
