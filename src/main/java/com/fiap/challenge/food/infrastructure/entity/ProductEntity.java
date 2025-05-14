package com.fiap.challenge.food.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    private String id;
    private String name;
    private String description;
    private Set<String> images;
    private BigDecimal price;
    private String category;
    private String status;
}
