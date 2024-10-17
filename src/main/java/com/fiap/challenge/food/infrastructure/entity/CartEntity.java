package com.fiap.challenge.food.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long consumerId;

    @OneToMany(mappedBy = "cart", cascade = ALL)
    private List<CartItemEntity> items;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @Enumerated(STRING)
    private CartStatusEntity status;

    public void setItems(List<CartItemEntity> items) {
        this.items = items;
        items.forEach(item -> item.setCart(this));
    }
}
