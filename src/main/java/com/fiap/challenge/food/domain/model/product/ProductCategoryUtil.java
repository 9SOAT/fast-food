package com.fiap.challenge.food.domain.model.product;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ProductCategoryUtil {

    private static final Map<String, Integer> CATEGORY_ORDER_MAP = Map.of(
        "SANDWICH", 1,
        "DRINK", 2,
        "SIDE_DISH", 3,
        "DESSERT", 4
    );

    private ProductCategoryUtil() {
    }

    public static Boolean isSubsequent(String currentCategory, String newCategory) {

        Integer currentOrder = CATEGORY_ORDER_MAP.get(currentCategory);
        Integer newOrder = CATEGORY_ORDER_MAP.get(newCategory);
        if (currentOrder == null || newOrder == null) {
            throw new IllegalArgumentException("Invalid category");
        }
        return currentOrder > newOrder;
    }

    public static String getGreatestCategory(List<String> categories) {
        return categories.stream()
            .max(Comparator.comparingInt(CATEGORY_ORDER_MAP::get))
            .orElseThrow(() -> new IllegalArgumentException("No categories provided"));
    }

    public static Integer getOrder(String category) {
        Integer order = CATEGORY_ORDER_MAP.get(category);
        if (order == null) {
            throw new IllegalArgumentException("Invalid category");
        }
        return order;
    }
}
