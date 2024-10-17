package com.fiap.challenge.food.domain.model.order;

import com.fiap.challenge.food.domain.model.product.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long id;
    @NonNull
    private Long productId;
    @NonNull
    private String productName;
    @NonNull
    private ProductCategory productCategory;
    @NonNull
    private Integer quantity;
    @NonNull
    private BigDecimal price;
    @NonNull
    private BigDecimal subtotal;
}
