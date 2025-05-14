package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import com.fiap.challenge.food.infrastructure.integration.FastFoodCatalogClient;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExternalProductRepository implements ProductRepository {

    private final FastFoodCatalogClient fastFoodCatalogClient;
    private final EntityMapper entityMapper;

    public ExternalProductRepository(FastFoodCatalogClient fastFoodCatalogClient, EntityMapper entityMapper) {
        this.fastFoodCatalogClient = fastFoodCatalogClient;
        this.entityMapper = entityMapper;
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.of(entityMapper.toProduct(fastFoodCatalogClient.findById(id)));
    }
}
