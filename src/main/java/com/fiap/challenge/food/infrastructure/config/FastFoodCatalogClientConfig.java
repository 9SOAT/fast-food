package com.fiap.challenge.food.infrastructure.config;

import com.fiap.challenge.food.infrastructure.integration.FastFoodCatalogClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class FastFoodCatalogClientConfig {


    @Value("${app.integration.catalog.base-url}")
    private String catalogBaseUrl;

    @Value("${app.integration.catalog.timeout:5000}")
    private long timeoutMillis;

    @Bean
    public RestClient catalogRestClient() {
        return RestClient.builder()
            .baseUrl(catalogBaseUrl)
            .defaultHeader("Content-Type", "application/json")
            .build();

    }

    @Bean
    public FastFoodCatalogClient fastFoodCatalogClient(RestClient restClient) {
        // Criando um adaptador do RestClient
        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        // Configurando o HttpServiceProxyFactory com o adaptador
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter)
//            .blockTimeout(Duration.ofMillis(timeoutMillis))
            .build();

        // Criando o proxy que implementa a interface FastFoodCatalogClient
        return factory.createClient(FastFoodCatalogClient.class);
    }
}
