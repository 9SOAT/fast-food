package com.fiap.challenge.food.domain.model.cart;

import com.fiap.challenge.food.domain.model.product.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
public class CartItem {
    private Long id;
    private Long productId;
    private String productName;
    private ProductCategory category;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime createdAt = LocalDateTime.now();

    public BigDecimal getSubTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
