package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.application.request.ProductMutation;
import com.fiap.challenge.food.application.response.ProductCategoryView;
import com.fiap.challenge.food.application.response.ProductView;
import com.fiap.challenge.food.domain.model.product.ProductStatus;

import java.math.BigDecimal;
import java.util.Set;

public class ProductDtoFixture {

    public static ProductView aProductDto() {
        return new ProductView(1L, "Product 1", "Description 1", Set.of("image1.jpg"), BigDecimal.TEN, ProductCategoryView.SANDWICH, ProductStatus.ACTIVE);
    }

    public static ProductMutation aMutation() {
        return new ProductMutation("Product 1", "Description 1", Set.of("image1.jpg"), BigDecimal.TEN, ProductCategoryView.SANDWICH);
    }

    public static ProductMutation mutationWithNullName() {
        return new ProductMutation(null, "Description 1", Set.of("image1.jpg"), BigDecimal.TEN, ProductCategoryView.SANDWICH);
    }

    public static ProductMutation mutationWithNullDescription() {
        return new ProductMutation("Product 1", null, Set.of("image1.jpg"), BigDecimal.TEN, ProductCategoryView.SANDWICH);
    }

    public static ProductMutation mutationWithEmptyImages() {
        return new ProductMutation("Product 1", "Description 1", Set.of(), BigDecimal.TEN, ProductCategoryView.SANDWICH);
    }

    public static ProductMutation mutationWithNullImages() {
        return new ProductMutation("Product 1", "Description 1", null, BigDecimal.TEN, ProductCategoryView.SANDWICH);
    }

    public static ProductMutation mutationWithNullPrice() {
        return new ProductMutation("Product 1", "Description 1", Set.of("http://image1.jpg"), null, ProductCategoryView.SANDWICH);
    }

    public static ProductMutation mutationWithNullCategory() {
        return new ProductMutation("Product 1", "Description 1", Set.of("http://image1.jpg"), BigDecimal.TEN, null);
    }
}
