package com.fiap.challenge.food.infrastructure.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", indexes = {
        @Index(name = "uk_product_name", columnList = "name", unique = true),
        @Index(name = "idx_product_category_status", columnList = "category, status"),
        @Index(name = "idx_product_status", columnList = "status")
})
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String description;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name="product_images")
    private Set<String> images;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Enumerated(STRING)
    private ProductCategoryEntity category;

    @Enumerated(STRING)
    @NotNull
    private ProductStatusEntity status;
}
