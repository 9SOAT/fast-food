package com.fiap.challenge.food.infrastructure.config;

import com.fiap.challenge.food.infrastructure.integration.FastFoodOrderClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

class FastFoodOrderClientConfigTest {


    @Test
    @DisplayName("Deve criar FastFoodOrderClient usando RestClient fornecido")
    void fastFoodOrderClientCreatesProxyWithRestClient() {
        FastFoodOrderClientConfig config = new FastFoodOrderClientConfig();
        RestClient restClient = mock(RestClient.class, RETURNS_DEEP_STUBS);
        FastFoodOrderClient client = config.fastFoodOrderClient(restClient);
        assertNotNull(client);
    }
}
