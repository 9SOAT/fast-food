package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.fixture.ProductEntityFixture;
import com.fiap.challenge.food.fixture.ProductFixture;
import com.fiap.challenge.food.infrastructure.integration.FastFoodCatalogClient;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExternalProductRepositoryTest {

    @Mock
    private FastFoodCatalogClient catalogClient;

    @Mock
    private EntityMapper entityMapperMock;

    @InjectMocks
    private ExternalProductRepository target;

    @Test
    void findById_whenProductExists_shouldReturnProduct() {
        String id = "1";
        Product product = ProductFixture.aProduct();
        when(catalogClient.findById(id))
            .thenReturn(ProductEntityFixture.aValidProductEntityWithId());

        when(entityMapperMock.toProduct(ProductEntityFixture.aValidProductEntityWithId()))
            .thenReturn(product);

        Optional<Product> found = target.findById(id);

        assertEquals(product, found.get());
    }
}
