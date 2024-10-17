package com.fiap.challenge.food.domain.model.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryTest {

    @Test
    void testCreateProductCategorySubsequent() {
        ProductCategory sandwich = ProductCategory.SANDWICH;
        ProductCategory drink = ProductCategory.DRINK;
        assertTrue(drink.isSubsequent(sandwich));
    }

    @Test
    void testCreateProductCategoryNotSubsequent() {
        ProductCategory drink = ProductCategory.DRINK;
        ProductCategory sandwich = ProductCategory.SANDWICH;
        assertFalse(sandwich.isSubsequent(drink));
    }

    @Test
    void testCreateProductCategoryNotSubsequentWithEqualsCategory() {
        ProductCategory sandwich = ProductCategory.SANDWICH;
        assertFalse(sandwich.isSubsequent(sandwich));
    }

    @Test
    void getOrderTest() {
        assertEquals(1, ProductCategory.SANDWICH.getOrder());
        assertEquals(2, ProductCategory.DRINK.getOrder());
        assertEquals(3, ProductCategory.SIDE_DISH.getOrder());
        assertEquals(4, ProductCategory.DESSERT.getOrder());
    }

}
