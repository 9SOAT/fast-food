package com.fiap.challenge.food.domain.model.cart;

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
    private String productId;
    private String productName;
    private String category;
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
