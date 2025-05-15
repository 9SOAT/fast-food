package com.fiap.challenge.food.infrastructure.config;

import com.fiap.challenge.food.infrastructure.integration.FastFoodOrderClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class FastFoodOrderClientConfig {


    @Value("${app.integration.order.base-url}")
    private String orderBaseUrl;

    @Value("${app.integration.order.timeout:5000}")
    private long timeoutMillis;

    @Bean
    public RestClient orderRestClient() {
        return RestClient.builder()
            .baseUrl(orderBaseUrl)
            .defaultHeader("Content-Type", "application/json")
            .build();

    }

    @Bean
    public FastFoodOrderClient fastFoodOrderClient(RestClient orderRestClient) {
        // Criando um adaptador do RestClient
        RestClientAdapter adapter = RestClientAdapter.create(orderRestClient);

        // Configurando o HttpServiceProxyFactory com o adaptador
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter)
//            .blockTimeout(Duration.ofMillis(timeoutMillis))
            .build();

        // Criando o proxy que implementa a interface FastFoodOrderClient
        return factory.createClient(FastFoodOrderClient.class);
    }
}
