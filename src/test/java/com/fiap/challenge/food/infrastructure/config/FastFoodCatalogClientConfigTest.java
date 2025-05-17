package com.fiap.challenge.food.infrastructure.config;

import com.fiap.challenge.food.ApplicationTests;
import com.fiap.challenge.food.infrastructure.integration.FastFoodCatalogClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class FastFoodCatalogClientConfigTest extends ApplicationTests {

    @Autowired
    FastFoodCatalogClient fastFoodCatalogClient;

    @Test
    void contextLoads_andClientIsCreated() {
        // just verify that Spring was able to wire up the proxy
        assertThat(fastFoodCatalogClient).isNotNull();
    }
}
