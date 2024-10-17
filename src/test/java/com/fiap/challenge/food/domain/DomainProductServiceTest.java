package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductStatus;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import com.fiap.challenge.food.fixture.ProductFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DomainProductServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;

    @InjectMocks
    private DomainProductService target;

    @Test
    void createProduct_savesAndReturnsProduct() {
        Product product = ProductFixture.aProduct();
        when(productRepositoryMock.save(any(Product.class))).thenReturn(product);

        Product result = target.create(product);

        assertEquals(product, result);
        verify(productRepositoryMock, times(1)).save(product);
    }

    @Test
    void findAll_ByStatus_returnsPagedProducts() {
        PageResult<Product> pageResult = new PageResult<>(List.of(ProductFixture.aProduct()), 0, 1, 1L, 1);
        List<ProductStatus> active = List.of(ProductStatus.ACTIVE);
        when(productRepositoryMock.findAllByStatus(active, 0, 1)).thenReturn(pageResult);

        PageResult<Product> result = target.findAllByStatus(active,0, 1);

        assertEquals(pageResult, result);
    }

    @Test
    void findById_returnsProduct() {
        Product product = ProductFixture.aProduct();
        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = target.findById(1L);

        assertEquals(product, result);
    }

    @Test
    void findById_throwsNotFoundException() {
        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> target.findById(1L));

        assertEquals("Product not found 1", exception.getMessage());
    }

    @Test
    void updateProduct_updatesAndReturnsProduct() {
        Product product = ProductFixture.aProduct();
        Product updatedProduct = ProductFixture.aProduct();
        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepositoryMock.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = target.update(1L, updatedProduct);

        assertEquals(updatedProduct, result);
    }

    @Test
    void deleteProduct_inactivatesProduct() {
        Product product = ProductFixture.aProduct();
        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(product));

        target.delete(1L);

        verify(productRepositoryMock, times(1)).save(product);
    }

}
