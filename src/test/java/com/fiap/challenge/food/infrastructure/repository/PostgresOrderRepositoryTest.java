package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.fixture.OrderFixture;
import com.fiap.challenge.food.infrastructure.entity.OrderEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PostgresOrderRepositoryTest {

    @Mock
    private JpaOrderRepository jpaOrderRepositoryMock;

    @Mock
    private EntityMapper entityMapperMock;

    @InjectMocks
    private PostgresOrderRepository postgresOrderRepository;

    @Test
    void saveOrderSuccessfully() {
        Order order = OrderFixture.validOrder();
        OrderEntity orderEntity = new OrderEntity();
        OrderEntity savedOrderEntity = new OrderEntity();
        Order savedOrder = OrderFixture.validOrder();

        when(entityMapperMock.toOrderEntity(order)).thenReturn(orderEntity);
        when(jpaOrderRepositoryMock.save(orderEntity)).thenReturn(savedOrderEntity);
        when(entityMapperMock.toOrder(savedOrderEntity)).thenReturn(savedOrder);

        Order result = postgresOrderRepository.save(order);

        assertNotNull(result);
        assertEquals(savedOrder, result);
        verify(entityMapperMock, times(1)).toOrderEntity(order);
        verify(jpaOrderRepositoryMock, times(1)).save(orderEntity);
        verify(entityMapperMock, times(1)).toOrder(savedOrderEntity);
    }

    @Test
    void findAllByStatusInSuccessfully() {
        OrderEntity orderEntity = new OrderEntity();
        Order order = OrderFixture.validOrder();
        when(entityMapperMock.toOrder(orderEntity)).thenReturn(order);

        when(jpaOrderRepositoryMock.findAllByStatusIn(any(), any()))
            .thenReturn(new PageImpl<>(List.of(orderEntity), PageRequest.of(0,10), 1));

        postgresOrderRepository.findAllByStatusIn(Collections.emptyList(), 1, 10);

        verify(jpaOrderRepositoryMock, times(1)).findAllByStatusIn(any(), any());
    }

    @Test
    void findByPaymentTransactionIdSuccessfully() {
        OrderEntity orderEntity = new OrderEntity();
        Order order = OrderFixture.validOrder();
        when(entityMapperMock.toOrder(orderEntity)).thenReturn(order);
        when(jpaOrderRepositoryMock.findByPaymentTransactionId(any())).thenReturn(Optional.of(orderEntity));

        postgresOrderRepository.findByPaymentTransactionId(null);

        verify(jpaOrderRepositoryMock, times(1)).findByPaymentTransactionId(any());
    }

    @Test
    void findByIdSuccessfully() {
        OrderEntity orderEntity = new OrderEntity();
        Order order = OrderFixture.validOrder();
        when(entityMapperMock.toOrder(orderEntity)).thenReturn(order);

        when(jpaOrderRepositoryMock.findById(any())).thenReturn(Optional.of(orderEntity));

        postgresOrderRepository.findById(null);

        verify(jpaOrderRepositoryMock, times(1)).findById(null);
    }
}
