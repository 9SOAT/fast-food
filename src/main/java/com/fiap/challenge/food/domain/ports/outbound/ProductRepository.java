package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.product.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(String id);
}
