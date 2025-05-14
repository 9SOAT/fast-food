package com.fiap.challenge.food.infrastructure.integration;

import com.fiap.challenge.food.infrastructure.entity.ProductEntity;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FastFoodCatalogClientTest {

    private WireMockServer wireMockServer;
    private FastFoodCatalogClient fastFoodCatalogClient;

    @BeforeEach
    void setUp() {
        // Iniciar o WireMock Server em uma porta aleatória
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());

        // Configurar o RestClient e o FastFoodCatalogClient
        RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:" + wireMockServer.port())
            .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter)
            .build();

        fastFoodCatalogClient = factory.createClient(FastFoodCatalogClient.class);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void findById_shouldReturnProduct_whenProductExists() {
        // Arrange
        String productId = "1";

        // Stub para o WireMock
        stubFor(get(urlEqualTo("/products/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{"
                    + "\"id\": 1,"
                    + "\"name\": \"Hamburger\","
                    + "\"description\": \"Delicious hamburger\","
                    + "\"price\": 12.50,"
                    + "\"category\": \"SANDWICH\","
                    + "\"status\": \"ACTIVE\""
                    + "}")));

        // Act
        ProductEntity product = fastFoodCatalogClient.findById(productId);

        // Assert
        assertNotNull(product);
        assertEquals("1", product.getId());
        assertEquals("Hamburger", product.getName());
        assertEquals("Delicious hamburger", product.getDescription());
    }

    @Test
    void findById_shouldHandleError_whenProductDoesNotExist() {
        // Arrange
        String productId = "999";

        // Stub para o WireMock
        stubFor(get(urlEqualTo("/products/999"))
            .willReturn(aResponse()
                .withStatus(404)
                .withHeader("Content-Type", "application/json")
                .withBody("{"
                    + "\"status\": 404,"
                    + "\"message\": \"Product not found\""
                    + "}")));

        // Act & Assert - Verificando que a exceção é lançada
        try {
            fastFoodCatalogClient.findById(productId);
            // Se chegar aqui, o teste falhou
            assert false : "Should have thrown an exception";
        } catch (Exception e) {
            // Verificar que é uma exceção relacionada ao HTTP 404
            assertThat(e.getMessage()).contains("404");
        }
    }
}
