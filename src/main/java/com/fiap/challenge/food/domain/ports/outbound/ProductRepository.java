package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductCategory;
import com.fiap.challenge.food.domain.model.product.ProductStatus;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    PageResult<Product> findAllByCategory(ProductCategory category, int page, int size);

    Optional<Product> findById(Long id);

    Product save(Product product);

    PageResult<Product> findAllByStatus(List<ProductStatus> statuses, int page, int size);
}
