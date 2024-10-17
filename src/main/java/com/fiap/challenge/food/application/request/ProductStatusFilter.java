package com.fiap.challenge.food.application.request;

import com.fiap.challenge.food.domain.model.product.ProductStatus;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ProductStatusFilter {
    ACTIVE(List.of(ProductStatus.ACTIVE)),
    INACTIVE(List.of(ProductStatus.INACTIVE)),
    ALL(Arrays.stream(ProductStatus.values()).toList());

    private final List<ProductStatus> statuses;

    ProductStatusFilter(List<ProductStatus> statuses) {
        this.statuses = statuses;
    }
}
