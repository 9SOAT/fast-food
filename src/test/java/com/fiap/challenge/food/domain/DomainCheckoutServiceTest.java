package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.exception.UnprocessableEntityException;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.ports.outbound.CartRepository;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.domain.ports.outbound.PaymentClient;
import com.fiap.challenge.food.domain.ports.outbound.PaymentRepository;
import com.fiap.challenge.food.fixture.CartFixture;
import com.fiap.challenge.food.fixture.OrderFixture;
import com.fiap.challenge.food.fixture.PaymentFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DomainCheckoutServiceTest {

    @Mock
    private OrderRepository orderRepositoryMock;

    @Mock
    private CartRepository cartRepositoryMock;

    @Mock
    private PaymentClient paymentClientMock;

    @Mock
    private PaymentRepository paymentRepositoryMock;

    @InjectMocks
    private DomainCheckoutService domainCheckoutService;

    @Test
    void checkoutSuccessfully() {
        Cart cart = CartFixture.aCartWithASandwich();

        Order order = OrderFixture.validOrder();
        Payment payment = PaymentFixture.aPixPayment();

        when(cartRepositoryMock.findById(1L)).thenReturn(Optional.of(cart));
        when(paymentClientMock.create(any(Payment.class))).thenReturn(payment);
        when(paymentRepositoryMock.save(any(Payment.class))).thenReturn(payment);
        when(orderRepositoryMock.save(any(Order.class))).thenReturn(order);

        Order result = domainCheckoutService.checkout(1L);

        assertNotNull(result);
        verify(cartRepositoryMock, times(1)).findById(1L);
        verify(cartRepositoryMock, times(1)).save(cart);
        verify(paymentClientMock, times(1)).create(any(Payment.class));
        verify(paymentRepositoryMock, times(1)).save(any(Payment.class));
        verify(orderRepositoryMock, times(1)).save(any(Order.class));
    }

    @Test
    void checkoutWithNonExistentCart() {
        when(cartRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> domainCheckoutService.checkout(1L));

        assertEquals("Cart not found id=1", exception.getMessage());
        verify(cartRepositoryMock, times(1)).findById(1L);
    }

    @Test
    void checkoutWithClosedCart() {
        Cart cart = mock(Cart.class);
        when(cart.isOpenStatus()).thenReturn(false);
        when(cartRepositoryMock.findById(1L)).thenReturn(Optional.of(cart));

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> domainCheckoutService.checkout(1L));

        assertEquals("Cart is not open", exception.getMessage());
        verify(cartRepositoryMock, times(1)).findById(1L);
    }

    @Test
    void checkoutWithEmptyCart() {
        Cart cart = mock(Cart.class);
        when(cart.isOpenStatus()).thenReturn(true);
        when(cart.isEmpty()).thenReturn(true);
        when(cartRepositoryMock.findById(1L)).thenReturn(Optional.of(cart));

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> domainCheckoutService.checkout(1L));

        assertEquals("Cart is empty", exception.getMessage());
        verify(cartRepositoryMock, times(1)).findById(1L);
    }

    @Test
    void checkoutWithInvalidCartTotal() {
        Cart cart = mock(Cart.class);
        when(cart.isOpenStatus()).thenReturn(true);
        when(cart.isEmpty()).thenReturn(false);
        when(cart.isCartTotalGreaterThanZero()).thenReturn(false);
        when(cartRepositoryMock.findById(1L)).thenReturn(Optional.of(cart));

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> domainCheckoutService.checkout(1L));

        assertEquals("Cart total should be greater than zero", exception.getMessage());
        assertEquals("CART_TOTAL_INVALID", exception.getCode());
        verify(cartRepositoryMock, times(1)).findById(1L);
    }

}
