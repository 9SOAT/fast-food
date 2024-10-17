package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductCategory;
import com.fiap.challenge.food.domain.ports.inbound.CatalogService;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;

public class DomainCatalogService implements CatalogService {

    private final ProductRepository productRepository;

    public DomainCatalogService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public PageResult<Product> findAllByCategory(ProductCategory category, int page, int size) {
        return productRepository.findAllByCategory(category, page, size);
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Product not found id=%d", id), "PRODUCT_NOT_FOUND"));
    }
}
