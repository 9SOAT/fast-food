package com.fiap.challenge.food.domain.model.order;

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
    private String productId;
    @NonNull
    private String productName;
    @NonNull
    private String productCategory;
    @NonNull
    private Integer quantity;
    @NonNull
    private BigDecimal price;
    @NonNull
    private BigDecimal subtotal;

    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + id +
            ", productId='" + productId + '\'' +
            ", productName='" + productName + '\'' +
            ", productCategory='" + productCategory + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            ", subtotal=" + subtotal +
            '}';
    }

}
