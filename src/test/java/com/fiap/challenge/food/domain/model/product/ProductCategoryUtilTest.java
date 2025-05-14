package com.fiap.challenge.food.domain.model.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryUtilTest {

    @Test
    void testCreateProductCategorySubsequent() {
        String sandwich = "SANDWICH";
        String drink = "DRINK";
        assertTrue(ProductCategoryUtil.isSubsequent(drink, sandwich));
    }

    @Test
    void testCreateProductCategoryNotSubsequent() {
        String drink = "DRINK";
        String sandwich = "SANDWICH";
        assertFalse(ProductCategoryUtil.isSubsequent(sandwich, drink));
    }

    @Test
    void testCreateProductCategoryNotSubsequentWithEqualsCategory() {
        String sandwich = "SANDWICH";
        assertFalse(ProductCategoryUtil.isSubsequent(sandwich, sandwich));
    }

    @Test
    void getOrderTest() {
        assertEquals(1, ProductCategoryUtil.getOrder("SANDWICH"));
        assertEquals(2, ProductCategoryUtil.getOrder("DRINK"));
        assertEquals(3, ProductCategoryUtil.getOrder("SIDE_DISH"));
        assertEquals(4, ProductCategoryUtil.getOrder("DESSERT"));
    }

}
