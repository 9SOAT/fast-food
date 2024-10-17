package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.exception.UnprocessableEntityException;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.order.OrderStatus;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.fixture.OrderFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DomainOrderServiceTest {

    @Mock
    private OrderRepository orderRepositoryMock;

    @InjectMocks
    private DomainOrderService target;

    @Test
    void testApprovePayment() {
        // Arrange
        Long orderId = 1L;
        Order order = OrderFixture.validOrder();
        order.setId(orderId);
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        target.approvePayment(orderId);

        // Assert
        verify(orderRepositoryMock).save(ArgumentMatchers.argThat(o ->
            o.getStatus() == OrderStatus.READY_FOR_PREPARATION &&
                order.getPayment().getStatus() == PaymentStatus.APPROVED &&
                order.getPayment().getApprovedAt() != null
        ));
    }

    @Test
    void testApprovePaymentWithOrderNotFound() {
        // Arrange
        Long orderId = 1L;
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> target.approvePayment(orderId));

        // Assert
        assertEquals("Order not found. ID: 1", exception.getMessage());
    }

    @Test
    void testApprovePaymentWithOrderNotWaitingPayment() {
        // Arrange
        Long orderId = 1L;
        Order order = OrderFixture.validOrder();
        order.setId(orderId);
        order.setStatus(OrderStatus.READY_FOR_PREPARATION);
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> target.approvePayment(orderId));

        // Assert
        assertEquals("Order is not waiting for payment. ID: 1", exception.getMessage());
    }

    @Test
    void testApprovePaymentWithTransactionId() {
        // Arrange
        String transactionId = "transaction-id";
        Order order = OrderFixture.validOrder();
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        when(orderRepositoryMock.findByPaymentTransactionId(transactionId)).thenReturn(Optional.of(order));

        // Act
        target.approvePayment(transactionId);

        // Assert
        verify(orderRepositoryMock).save(ArgumentMatchers.argThat(o ->
            o.getStatus() == OrderStatus.READY_FOR_PREPARATION &&
                order.getPayment().getStatus() == PaymentStatus.APPROVED &&
                order.getPayment().getApprovedAt() != null
        ));
    }

    @Test
    void testApprovePaymentWithTransactionIdAndOrderNotFound() {
        // Arrange
        String transactionId = "transaction-id";
        when(orderRepositoryMock.findByPaymentTransactionId(transactionId)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> target.approvePayment(transactionId));

        // Assert
        assertEquals("Order not found. Transaction ID: transaction-id", exception.getMessage());
    }

    @Test
    void testApprovePaymentWithTransactionIdAndOrderNotWaitingPayment() {
        // Arrange
        String transactionId = "transaction-id";
        Order order = OrderFixture.validOrder();
        order.setStatus(OrderStatus.READY_FOR_PREPARATION);
        when(orderRepositoryMock.findByPaymentTransactionId(transactionId)).thenReturn(Optional.of(order));

        // Act
        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class, () -> target.approvePayment(transactionId));

        // Assert
        assertEquals("Order is not waiting for payment. ID: 1", exception.getMessage());
    }

    @Test
    void testGetAllByStatus() {
        // Arrange
        OrderStatus status = OrderStatus.WAITING_PAYMENT;
        when(orderRepositoryMock.findAllByStatusIn(ArgumentMatchers.anyList(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenReturn(OrderFixture.pageResultWithOneOrder());

        // Act
        var result = target.getAllByStatus(List.of(status), 0, 10);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }

}
