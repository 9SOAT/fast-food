package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.ports.outbound.PaymentRepository;
import com.fiap.challenge.food.infrastructure.entity.PaymentEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostgresPaymentRepository implements PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;
    private final EntityMapper entityMapper;

    public PostgresPaymentRepository(JpaPaymentRepository jpaPaymentRepository, EntityMapper entityMapper) {
        this.jpaPaymentRepository = jpaPaymentRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return jpaPaymentRepository.findById(id)
            .map(entityMapper::toPayment);
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity saved = jpaPaymentRepository.save(entityMapper.toPaymentEntity(payment));
        return entityMapper.toPayment(saved);
    }

}
