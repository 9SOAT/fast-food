package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.application.response.ProductCategoryView;
import com.fiap.challenge.food.application.response.ProductView;
import com.fiap.challenge.food.domain.model.product.ProductStatus;

import java.math.BigDecimal;
import java.util.Set;

public class ProductViewFixture {
    public static ProductView aProductViewWithId() {
        return new ProductView(1L, "Product 1", "Description 1", Set.of("image1.jpg"), BigDecimal.TEN, ProductCategoryView.SANDWICH, ProductStatus.ACTIVE);
    }
}
