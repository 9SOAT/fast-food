package com.fiap.challenge.food.infrastructure.integration;

import com.fiap.challenge.food.infrastructure.entity.ProductEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;


public interface FastFoodCatalogClient {

    @GetExchange(value = "/products/{id}")
    ProductEntity findById(@PathVariable String id);

}
