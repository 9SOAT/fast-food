package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.model.cart.CartItem;
import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.exception.UnprocessableEntityException;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.ports.outbound.CartRepository;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import com.fiap.challenge.food.fixture.CartFixture;
import com.fiap.challenge.food.fixture.ProductFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DomainCartServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private DomainCartService domainCartService;

    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCartWithValidConsumerId() {
        String consumerId = "08444331015";
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart cart = domainCartService.create(consumerId);

        assertNotNull(cart);
        assertEquals(consumerId, cart.getConsumerId());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void getCartByIdWhenExists() {
        Long cartId = 1L;
        Cart cart = CartFixture.aEmptyCart();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Cart foundCart = domainCartService.getById(cartId);

        assertNotNull(foundCart);
        assertEquals(cartId, foundCart.getId());
    }

    @Test
    void getCartByIdWhenNotExists() {
        Long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> domainCartService.getById(cartId));

        assertEquals("Cart not found id=1", exception.getMessage());
        assertEquals("CART_NOT_FOUND", exception.getCode());
    }

    @Test
    void addItemToCartWithValidProductAndSequence() {
        Long cartId = 1L;
        String productId = "1";
        int quantity = 2;
        Product product = ProductFixture.aProduct();
        Cart cart = CartFixture.aEmptyCart();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart updatedCart = domainCartService.addItem(cartId, productId, quantity);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getItems().size());
        CartItem item = updatedCart.getItems().get(0);
        assertEquals(productId, item.getProductId());
        assertEquals(quantity, item.getQuantity());
    }

    @Test
    void addItemToCartWithInvalidProduct() {
        Long cartId = 1L;
        String productId = "1";
        int quantity = 2;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> domainCartService.addItem(cartId, productId, quantity));

        assertEquals("Product not found 1", exception.getMessage());
        assertEquals("PRODUCT_NOT_FOUND", exception.getCode());
    }

    @Test
    void addItemToCartWithInvalidSequence() {
        Long cartId = 1L;
        String productId = "1";
        int quantity = 2;
        Product sandwich = ProductFixture.aProduct();
        Cart cart = CartFixture.aCartWithASandwich();
        when(productRepository.findById(productId)).thenReturn(Optional.of(sandwich));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> domainCartService.addItem(cartId, productId, quantity));

        assertEquals("Invalid addition sequence of items", exception.getMessage());
        assertEquals("INVALID_ITEM_SEQUENCE", exception.getCode());
    }

    @Test
    void addItemToNonExistentCart() {
        Long cartId = 1L;
        String productId = "1";
        int quantity = 2;
        Product product = ProductFixture.aProduct();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> domainCartService.addItem(cartId, productId, quantity));

        assertEquals("Cart not found id=1", exception.getMessage());
        assertEquals("CART_NOT_FOUND", exception.getCode());
    }

    @Test
    void createCartWithNullConsumerId() {
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart cart = domainCartService.create(null);

        assertNotNull(cart);
        assertNull(cart.getConsumerId());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

}
