package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.infrastructure.entity.OrderEntity;
import com.fiap.challenge.food.infrastructure.entity.PaymentEntity;
import com.fiap.challenge.food.infrastructure.integration.FastFoodOrderClient;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExternalOrderRepositoryTest {

    @Mock
    private FastFoodOrderClient fastFoodOrderClient;

    @Mock
    private EntityMapper entityMapper;

    @InjectMocks
    private ExternalOrderRepository externalOrderRepository;

    public ExternalOrderRepositoryTest() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void save_ShouldReturnMappedOrder_WhenSaveOperationIsSuccessful() {
        // Arrange
        Order inputOrder = mock(Order.class);
        OrderEntity savedOrderEntity = mock(OrderEntity.class);

        // Mock the behavior of the FastFoodOrderClient and EntityMapper
        when(fastFoodOrderClient.saveOrder(inputOrder)).thenReturn(savedOrderEntity);

        PaymentEntity mappedEntity = mock(PaymentEntity.class);
        when(entityMapper.toPaymentEntity(inputOrder.getPayment())).thenReturn(mappedEntity);

        Order mappedOrder = mock(Order.class);
        when(entityMapper.toOrder(savedOrderEntity)).thenReturn(mappedOrder);

        // Act
        Order actualOrder = externalOrderRepository.save(inputOrder);

        // Assert
        verify(fastFoodOrderClient, times(1)).saveOrder(inputOrder);
        verify(entityMapper, times(1)).toPaymentEntity(inputOrder.getPayment());
        verify(entityMapper, times(1)).toOrder(savedOrderEntity);
        assertEquals(mappedOrder, actualOrder);
    }

    @Test
    void save_ShouldLogSuccessfulSave_WhenSaveOperationIsSuccessful() {
        // Arrange
        Order inputOrder = mock(Order.class);
        OrderEntity savedOrderEntity = mock(OrderEntity.class);

        PaymentEntity mappedEntity = mock(PaymentEntity.class);

        when(fastFoodOrderClient.saveOrder(inputOrder)).thenReturn(savedOrderEntity);
        when(entityMapper.toPaymentEntity(inputOrder.getPayment())).thenReturn(mappedEntity);
        when(entityMapper.toOrder(savedOrderEntity)).thenReturn(mock(Order.class));

        // Act
        externalOrderRepository.save(inputOrder);

        // Assert
        verify(fastFoodOrderClient, times(1)).saveOrder(inputOrder);
        verify(savedOrderEntity, times(1)).getId(); // Ensures that the log statement is interacting with the entity as expected
    }

    @Test
    void approvePayment_ShouldCallFastFoodOrderClientOnce_WhenTransactionIdIsValid() {
        // Arrange
        String transactionId = "valid-transaction-id";

        // Act
        externalOrderRepository.approvePayment(transactionId);

        // Assert
        verify(fastFoodOrderClient, times(1)).approvePayment(transactionId);
    }

    @Test
    void rejectPayment_ShouldCallFastFoodOrderClientOnce_WhenTransactionIdIsValid() {
        // Arrange
        String transactionId = "valid-transaction-id";

        // Act
        externalOrderRepository.rejectPayment(transactionId);

        // Assert
        verify(fastFoodOrderClient, times(1)).rejectPayment(transactionId);
    }
}
