package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.model.cart.CartItem;
import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.exception.UnprocessableEntityException;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.order.OrderItem;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.ports.inbound.CheckoutService;
import com.fiap.challenge.food.domain.ports.outbound.CartRepository;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.domain.ports.outbound.PaymentClient;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.util.List;

import static com.fiap.challenge.food.domain.model.order.OrderStatus.WAITING_PAYMENT;

@Log4j2
public class DomainCheckoutService implements CheckoutService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final PaymentClient paymentClient;

    public DomainCheckoutService(OrderRepository orderRepository,
                                 CartRepository cartRepository, PaymentClient paymentClient) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.paymentClient = paymentClient;
    }

    @Override
    @Transactional
    public Order checkout(Long cartId) {
        log.info("Checking out cart id={}", cartId);
        Cart cart = getCart(cartId);
        validateCart(cart);
        cart.checkout();
        cartRepository.save(cart);
        Payment payment = createPayment(cart);
        return createOrder(cart, payment);
    }

    private Order createOrder(Cart cart, Payment payment) {
        log.info("Creating order for cartId={}", cart.getId());
        Order order = buildOrder(cart, payment);
        return orderRepository.save(order);
    }

    private Payment createPayment(Cart cart) {
        Payment payment = new Payment(cart.getTotal());
        return paymentClient.create(payment);
    }

    private Order buildOrder(Cart cart, Payment payment) {
        return Order.builder()
            .items(toOrderItems(cart.getItems()))
            .consumerId(cart.getConsumerId())
            .status(WAITING_PAYMENT)
            .total(cart.getTotal())
            .cartId(cart.getId())
            .payment(payment)
            .createdAt(Instant.now())
            .build();
    }

    private static void validateCart(Cart cart) {
        if (!cart.isOpenStatus()) {
            log.warn("Cart is not open. cartId={}", cart.getId());
            throw new UnprocessableEntityException("Cart is not open", "CART_NOT_OPEN");
        }

        if (cart.isEmpty()) {
            log.warn("Cart is empty. cartId={}", cart.getId());
            throw new UnprocessableEntityException("Cart is empty", "CART_EMPTY");
        }

        if (!cart.isCartTotalGreaterThanZero()) {
            log.warn("Cart total is invalid. cartId={}", cart.getId());
            throw new UnprocessableEntityException("Cart total should be greater than zero", "CART_TOTAL_INVALID");
        }
    }

    private List<OrderItem> toOrderItems(List<CartItem> cartItems) {
        return cartItems.stream().map(this::toOrderItem).toList();
    }

    private OrderItem toOrderItem(CartItem cartItem) {
        return new OrderItem(
            cartItem.getProductId(),
            cartItem.getProductName(),
            cartItem.getCategory(),
            cartItem.getQuantity(),
            cartItem.getPrice(),
            cartItem.getSubTotal());
    }

    private Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
            .orElseThrow(() -> new NotFoundException(String.format("Cart not found id=%d", cartId), "CART_NOT_FOUND"));
    }
}
