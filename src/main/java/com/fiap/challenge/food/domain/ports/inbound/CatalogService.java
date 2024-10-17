package com.fiap.challenge.food.domain.ports.inbound;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductCategory;

public interface CatalogService {

    public PageResult<Product> findAllByCategory(ProductCategory category, int page, int size);

    public Product getById(Long id);

}
