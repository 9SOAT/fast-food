package com.fiap.challenge.food.infrastructure.config;

import com.fiap.challenge.food.domain.*;
import com.fiap.challenge.food.domain.ports.inbound.OrderService;
import com.fiap.challenge.food.domain.ports.inbound.ProductService;
import com.fiap.challenge.food.domain.ports.outbound.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new DomainProductService(productRepository);
    }

    @Bean
    public DomainCatalogService catalogService(ProductRepository productRepository) {
        return new DomainCatalogService(productRepository);
    }

    @Bean
    public DomainCheckoutService checkoutService(OrderRepository orderRepository, CartRepository cartRepository, PaymentClient paymentClient) {
        return new DomainCheckoutService(orderRepository, cartRepository, paymentClient);
    }

    @Bean
    public DomainWebhookService webhookService(WebhookRepository webhookRepository, OrderService orderService) {
        return new DomainWebhookService(webhookRepository, orderService);
    }
}
