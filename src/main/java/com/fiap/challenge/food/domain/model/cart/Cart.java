package com.fiap.challenge.food.domain.model.cart;

import com.fiap.challenge.food.domain.model.product.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.fiap.challenge.food.domain.model.cart.CartStatus.CHECKED_OUT;
import static com.fiap.challenge.food.domain.model.cart.CartStatus.OPEN;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Long id;
    private Long consumerId;
    private List<CartItem> items = new ArrayList<>();
    private CartStatus status = OPEN;
    private LocalDateTime createdAt;

    public Cart(Long consumerId) {
        this.consumerId = consumerId;
        this.createdAt = LocalDateTime.now();
    }

    public BigDecimal getTotal() {
        return items.stream()
            .map(CartItem::getSubTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Cart addItem(CartItem item) {
        Optional<CartItem> existentItem = items.stream()
            .filter(cartItem -> cartItem.getProductId().equals(item.getProductId()))
            .findFirst();

        existentItem.ifPresentOrElse(cartItem ->
            cartItem.increaseQuantity(cartItem.getQuantity()),
            () -> {
                items.add(item);
            }
        );
        return this;
    }

    public void checkout() {
        this.status = CHECKED_OUT;
    }

    public Boolean isOpenStatus() {
        return this.status.isOpen();
    }

    public Boolean isEmpty() {
        return this.items.isEmpty();
    }

    public Optional<ProductCategory> getLatestItemCategory() {
        return items.stream()
            .map(CartItem::getCategory)
            .max(Comparator.comparingInt(ProductCategory::getOrder));
    }

    public Boolean isCartTotalGreaterThanZero() {
        return this.getTotal().compareTo(BigDecimal.ZERO) > 0;
    }
}
