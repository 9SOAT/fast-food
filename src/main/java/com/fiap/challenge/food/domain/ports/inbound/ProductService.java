package com.fiap.challenge.food.domain.ports.inbound;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductStatus;

import java.util.List;

public interface ProductService {

    public Product create(Product product);

    public PageResult<Product> findAllByStatus(List<ProductStatus> statuses, int page, int size);

    public Product findById(Long id);

    public Product update(Long id, Product product);

    public void delete(Long id);
}
