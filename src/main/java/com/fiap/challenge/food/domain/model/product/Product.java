package com.fiap.challenge.food.domain.model.product;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

import static com.fiap.challenge.food.domain.model.product.ProductStatus.ACTIVE;
import static com.fiap.challenge.food.domain.model.product.ProductStatus.INACTIVE;

@Data
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private Set<String> images;
    private BigDecimal price;
    private ProductCategory category;
    private ProductStatus status;

    public void update(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.images = product.getImages();
        this.price = product.getPrice();
        this.category = product.getCategory();
    }

    public void inactivate() {
        this.status = INACTIVE;
    }

    public void activate() {
        this.status = ACTIVE;
    }
}
