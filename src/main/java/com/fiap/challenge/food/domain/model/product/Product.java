package com.fiap.challenge.food.domain.model.product;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private String description;
    private Set<String> images;
    private BigDecimal price;
    private String category;
    private String status;
}
