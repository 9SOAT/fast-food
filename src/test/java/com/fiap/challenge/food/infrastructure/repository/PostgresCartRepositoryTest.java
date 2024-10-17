package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.fixture.CartFixture;
import com.fiap.challenge.food.infrastructure.entity.CartEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PostgresCartRepositoryTest {

    @Mock
    private JpaCartRepository jpaCartRepositoryMock;

    @Mock
    private EntityMapper entityMapperMock;

    @InjectMocks
    private PostgresCartRepository postgresCartRepository;

    @Test
    void saveCartSuccessfully() {
        Cart cart = CartFixture.aCartWithASandwich();
        CartEntity cartEntity = new CartEntity();
        CartEntity savedCartEntity = new CartEntity();
        Cart savedCart = CartFixture.aCartWithASandwich();

        when(entityMapperMock.toCartEntity(cart)).thenReturn(cartEntity);
        when(jpaCartRepositoryMock.save(cartEntity)).thenReturn(savedCartEntity);
        when(entityMapperMock.toCart(savedCartEntity)).thenReturn(savedCart);

        Cart result = postgresCartRepository.save(cart);

        assertNotNull(result);
        assertEquals(savedCart, result);
        verify(entityMapperMock, times(1)).toCartEntity(cart);
        verify(jpaCartRepositoryMock, times(1)).save(cartEntity);
        verify(entityMapperMock, times(1)).toCart(savedCartEntity);
    }

    @Test
    void findByIdCartSuccessfully() {
        Cart cart = CartFixture.aCartWithASandwich();
        CartEntity cartEntity = new CartEntity();

        when(jpaCartRepositoryMock.findById(cart.getId())).thenReturn(java.util.Optional.of(cartEntity));
        when(entityMapperMock.toCart(cartEntity)).thenReturn(cart);

        Cart result = postgresCartRepository.findById(cart.getId()).get();

        assertNotNull(result);
        assertEquals(cart, result);
        verify(jpaCartRepositoryMock, times(1)).findById(cart.getId());
        verify(entityMapperMock, times(1)).toCart(cartEntity);
    }

}
