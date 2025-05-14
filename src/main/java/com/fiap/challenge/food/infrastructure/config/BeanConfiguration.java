package com.fiap.challenge.food.infrastructure.config;

import com.fiap.challenge.food.domain.DomainCatalogService;
import com.fiap.challenge.food.domain.DomainProductService;
import com.fiap.challenge.food.domain.DomainWebhookService;
import com.fiap.challenge.food.domain.ports.inbound.ProductService;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import com.fiap.challenge.food.infrastructure.restProvider.OrderProvider;
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
    public DomainWebhookService webhookService(WebhookRepository webhookRepository, OrderProvider orderProvider) {
        return new DomainWebhookService(webhookRepository, orderProvider);
    }
}
