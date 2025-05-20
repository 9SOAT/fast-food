package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.fixture.PaymentFixture;
import com.fiap.challenge.food.infrastructure.entity.PaymentEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostgresPaymentRepositoryTest {

    JpaPaymentRepository jpaPaymentRepositoryMock;

    EntityMapper entityMapperMock;

    PostgresPaymentRepository target;

    @BeforeEach
    void setUp() {
        jpaPaymentRepositoryMock = mock(JpaPaymentRepository.class);
        entityMapperMock = mock(EntityMapper.class);
        target = new PostgresPaymentRepository(jpaPaymentRepositoryMock, entityMapperMock);
    }


    @Test
    @DisplayName("should find a payment by transaction id")
    void shouldFindByTransactionId() {
        // Given
        String transactionId = "12345";
        PaymentEntity paymentEntity = new PaymentEntity();
        Payment payment = PaymentFixture.aPixPayment();

        when(jpaPaymentRepositoryMock.findByTransactionId(transactionId)).thenReturn(Optional.of(paymentEntity));
        when(entityMapperMock.toPayment(any(PaymentEntity.class))).thenReturn(payment);

        // When
        Optional<Payment> result = target.findByTransactionId(transactionId);

        // Then
        Assertions.assertThat(result).isPresent();
        verify(jpaPaymentRepositoryMock).findByTransactionId(transactionId);
        verify(entityMapperMock).toPayment(paymentEntity);

    }
}
