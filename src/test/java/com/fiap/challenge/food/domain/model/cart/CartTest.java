package com.fiap.challenge.food.domain.model.cart;

import com.fiap.challenge.food.domain.model.product.ProductCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class CartTest {

    @Test
    void cartEntityTotalPriceCalculation() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(null, 1L, "X-TUDO", ProductCategory.SANDWICH, new BigDecimal("20.00"), 1, LocalDateTime.now()));
        cart.addItem(new CartItem(null, 2L, "Casquinha de creme", ProductCategory.DESSERT, new BigDecimal("9.99"), 2, LocalDateTime.now()));
        assertEquals(BigDecimal.valueOf(39.98), cart.getTotal());
    }

    @Test
    void cartIsEmptyItems() {
        Cart cart = new Cart();
        assertTrue(cart.isEmpty());
    }

    @Test
    void cartIsNotEmptyItems() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(null, 1L, "X-TUDO", ProductCategory.SANDWICH, new BigDecimal("20.00"), 1, LocalDateTime.now()));
        cart.addItem(new CartItem(null, 1L, "Casquinha de creme", ProductCategory.DESSERT, new BigDecimal("9.99"), 2, LocalDateTime.now()));
        assertFalse(cart.isEmpty());
    }

    @Test
    void cartCheckout() {
        Cart cart = new Cart();
        cart.checkout();
        assertEquals(CartStatus.CHECKED_OUT, cart.getStatus());
    }

    @Test
    void cartIsOpenStatus() {
        Cart cart = new Cart();
        assertTrue(cart.isOpenStatus());
    }

    @Test
    void cartIsNotOpenStatus() {
        Cart cart = new Cart();
        cart.checkout();
        assertFalse(cart.isOpenStatus());
    }

    @Test
    void cartAddItem() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(null, 1L, "X-TUDO", ProductCategory.SANDWICH, new BigDecimal("20.00"), 1, LocalDateTime.now()));
        assertEquals(1, cart.getItems().size());
    }

    @Test
    void cartAddItemWithSameProduct() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(null, 1L, "X-TUDO", ProductCategory.SANDWICH, new BigDecimal("20.00"), 1, LocalDateTime.now()));
        cart.addItem(new CartItem(null, 1L, "X-TUDO", ProductCategory.SANDWICH, new BigDecimal("20.00"), 1, LocalDateTime.now()));
        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getItems().get(0).getQuantity());
    }

    @Test
    void cartAddItemWithDifferentProduct() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(null, 1L, "X-TUDO", ProductCategory.SANDWICH, new BigDecimal("20.00"), 1, LocalDateTime.now()));
        cart.addItem(new CartItem(null, 2L, "Casquinha de creme", ProductCategory.DESSERT, new BigDecimal("9.99"), 2, LocalDateTime.now()));
        assertEquals(2, cart.getItems().size());
    }

    @Test
    void cartGetLatestItemCategory() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(null, 1L, "X-TUDO", ProductCategory.SANDWICH, new BigDecimal("20.00"), 1, LocalDateTime.now()));
        cart.addItem(new CartItem(null, 2L, "Casquinha de creme", ProductCategory.DESSERT, new BigDecimal("9.99"), 2, LocalDateTime.now()));
        assertEquals(ProductCategory.DESSERT, cart.getLatestItemCategory().get());
    }

    @Test
    void cartIsCartTotalGreaterThanZero() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(null, 1L, "X-TUDO", ProductCategory.SANDWICH, new BigDecimal("20.00"), 1, LocalDateTime.now()));
        assertTrue(cart.isCartTotalGreaterThanZero());
    }

    @Test
    void cartIsCartTotalEqualsZero() {
        Cart cart = new Cart();
        assertFalse(cart.isCartTotalGreaterThanZero());
    }
}
