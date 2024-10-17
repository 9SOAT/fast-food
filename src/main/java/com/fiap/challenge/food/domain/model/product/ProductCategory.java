package com.fiap.challenge.food.domain.model.product;

public enum ProductCategory {
    SANDWICH(1),
    DRINK(2),
    SIDE_DISH(3),
    DESSERT(4);

    private final int order;

    private ProductCategory(Integer order) {
        this.order = order;
    }

    public Boolean isSubsequent(ProductCategory category) {
        return this.order > category.order;
    }

    public int getOrder() {
        return this.order;
    }
}
