package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductCategory;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import com.fiap.challenge.food.fixture.ProductFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DomainCatalogServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;

    @InjectMocks
    private DomainCatalogService target;

    @Test
    void shouldReturnCatalogWhenValidInput() {
        // Arrange
        Long productId = 1L;

        Product product = ProductFixture.aProduct();
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(product));

        // Act
         Product result = target.getById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(product, result);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenProductNotFound() {
        // Arrange
        Long productId = 1L;

        when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> target.getById(productId));

        assertEquals("Product not found id=1", notFoundException.getMessage());
        assertEquals("PRODUCT_NOT_FOUND", notFoundException.getCode());
    }

    @Test
    void shouldReturnCatalogPageWhenValidInput() {
        // Arrange
        ProductCategory category = ProductCategory.SANDWICH;
        int page = 1;
        int size = 10;

        PageResult<Product> pageResult = PageResult.<Product>builder()
                .content(List.of(ProductFixture.aProduct()))
                .totalElements(1)
                .pageNumber(page)
                .pageSize(size)
                .totalPages(1)
                .build();

        when(productRepositoryMock.findAllByCategory(category, page, size)).thenReturn(pageResult);

        // Act
        PageResult<Product> result = target.findAllByCategory(category, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(pageResult, result);
    }
}
