package com.fiap.challenge.food.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cart_item", indexes = {
        @Index(name = "uk_cart_item_cart_id_product_id", columnList = "cart_id, product_id", unique = true)
})
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    private Long productId;
    @NotEmpty
    private String productName;
    @NotNull
    @Enumerated(STRING)
    private ProductCategoryEntity category;
    @Min(1)
    @NotNull
    private Integer quantity;
    @NotNull
    @Column(scale = 2)
    private BigDecimal price;
    @NotNull
    @Column(scale = 2)
    private BigDecimal subTotal;

    @NotNull
    @JoinColumn(name = "cart_id")
    @ManyToOne(targetEntity = CartEntity.class)
    private CartEntity cart;
}
