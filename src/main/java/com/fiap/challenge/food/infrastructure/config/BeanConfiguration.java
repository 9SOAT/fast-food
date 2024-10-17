package com.fiap.challenge.food.infrastructure.config;

import com.fiap.challenge.food.domain.DomainCartService;
import com.fiap.challenge.food.domain.DomainCatalogService;
import com.fiap.challenge.food.domain.DomainCheckoutService;
import com.fiap.challenge.food.domain.DomainConsumerService;
import com.fiap.challenge.food.domain.DomainProductService;
import com.fiap.challenge.food.domain.ports.inbound.CartService;
import com.fiap.challenge.food.domain.ports.inbound.ConsumerService;
import com.fiap.challenge.food.domain.ports.inbound.ProductService;
import com.fiap.challenge.food.domain.ports.outbound.CartRepository;
import com.fiap.challenge.food.domain.ports.outbound.ConsumerRepository;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.domain.ports.outbound.PaymentClient;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CartService cartService(ConsumerRepository consumerRepository, CartRepository cartRepository, ProductRepository productRepository) {
        return new DomainCartService(consumerRepository, productRepository, cartRepository);
    }

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new DomainProductService(productRepository);
    }

    @Bean
    public ConsumerService consumerService(ConsumerRepository consumerRepository) {
        return new DomainConsumerService(consumerRepository);
    }

    @Bean
    public DomainCatalogService catalogService(ProductRepository productRepository) {
        return new DomainCatalogService(productRepository);
    }

    @Bean
    public DomainCheckoutService checkoutService(OrderRepository orderRepository, CartRepository cartRepository, PaymentClient paymentClient) {
        return new DomainCheckoutService(orderRepository, cartRepository, paymentClient);
    }
}
