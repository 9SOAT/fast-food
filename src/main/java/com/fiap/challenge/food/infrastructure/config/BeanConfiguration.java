package com.fiap.challenge.food.infrastructure.config;

import com.fiap.challenge.food.domain.DomainCartService;
import com.fiap.challenge.food.domain.DomainCheckoutService;
import com.fiap.challenge.food.domain.DomainWebhookService;
import com.fiap.challenge.food.domain.ports.inbound.CartService;
import com.fiap.challenge.food.domain.ports.outbound.CartRepository;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.domain.ports.outbound.PaymentClient;
import com.fiap.challenge.food.domain.ports.outbound.PaymentRepository;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DomainCheckoutService checkoutService(OrderRepository orderRepository, CartRepository cartRepository, PaymentClient paymentClient, PaymentRepository paymentRepository) {
        return new DomainCheckoutService(orderRepository, cartRepository, paymentClient, paymentRepository);
    }

    @Bean
    public DomainWebhookService webhookService(WebhookRepository webhookRepository, OrderRepository orderRepository, PaymentRepository paymentRepository) {
        return new DomainWebhookService(webhookRepository, orderRepository, paymentRepository);
    }

    @Bean
    public CartService cartService(CartRepository cartRepository, ProductRepository productRepository) {
        return new DomainCartService(productRepository, cartRepository);
    }
}
