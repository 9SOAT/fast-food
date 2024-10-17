package com.fiap.challenge.food.domain.model.product;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void update_updatesProductDetails() {
        Product originalProduct = new Product(1L, "Original Name", "Original Description", Set.of("image1.jpg"), new BigDecimal("10.00"), ProductCategory.SANDWICH, ProductStatus.ACTIVE);
        Product updatedProduct = new Product(1L, "Updated Name", "Updated Description", Set.of("image2.jpg"), new BigDecimal("20.00"), ProductCategory.DRINK, ProductStatus.ACTIVE);

        originalProduct.update(updatedProduct);

        assertEquals("Updated Name", originalProduct.getName());
        assertEquals("Updated Description", originalProduct.getDescription());
        assertEquals(Set.of("image2.jpg"), originalProduct.getImages());
        assertEquals(new BigDecimal("20.00"), originalProduct.getPrice());
        assertEquals(ProductCategory.DRINK, originalProduct.getCategory());
    }

    @Test
    void inactivate_setsStatusToInactive() {
        Product product = new Product(1L, "Name", "Description", Set.of("image1.jpg"), new BigDecimal("10.00"), ProductCategory.SANDWICH, ProductStatus.ACTIVE);

        product.inactivate();

        assertEquals(ProductStatus.INACTIVE, product.getStatus());
    }

    @Test
    void activate_setsStatusToActive() {
        Product product = new Product(1L, "Name", "Description", Set.of("image1.jpg"), new BigDecimal("10.00"), ProductCategory.SANDWICH, ProductStatus.INACTIVE);

        product.activate();

        assertEquals(ProductStatus.ACTIVE, product.getStatus());
    }

    @Test
    void update_withNullValues_doesNotChangeFields() {
        Product originalProduct = new Product(1L, "Original Name", "Original Description", Set.of("image1.jpg"), new BigDecimal("10.00"), ProductCategory.SANDWICH, ProductStatus.ACTIVE);
        Product updatedProduct = new Product(1L, null, null, null, null, null, ProductStatus.ACTIVE);

        originalProduct.update(updatedProduct);

        assertEquals(null, originalProduct.getName());
        assertEquals(null, originalProduct.getDescription());
        assertEquals(null, originalProduct.getImages());
        assertEquals(null, originalProduct.getPrice());
        assertEquals(null, originalProduct.getCategory());
    }

}
