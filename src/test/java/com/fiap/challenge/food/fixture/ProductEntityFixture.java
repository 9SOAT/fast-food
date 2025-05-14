package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.infrastructure.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.Set;


public class ProductEntityFixture {

    public static ProductEntity aValidProductEntity() {
        return new ProductEntity(null, "Pizza de calabresa", "massa tradicional, molho de tomate artezanal, calabresa fatiada e cebola", Set.of(""), new BigDecimal(10), "SANDWICH", "ACTIVE");
    }

    public static ProductEntity aValidProductEntityWithId() {
        return new ProductEntity("1", "Pizza de calabresa", "massa tradicional, molho de tomate artezanal, calabresa fatiada e cebola", Set.of(""), new BigDecimal(10), "SANDWICH", "ACTIVE");
    }
}
