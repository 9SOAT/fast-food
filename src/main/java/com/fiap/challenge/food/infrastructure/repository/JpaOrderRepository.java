package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.infrastructure.entity.OrderEntity;
import com.fiap.challenge.food.infrastructure.entity.OrderStatusEntity;
import com.fiap.challenge.food.infrastructure.entity.PaymentEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findAllByStatusIn(Collection<@NotNull OrderStatusEntity> statuses, Pageable pageable);
    Optional<OrderEntity> findByPaymentTransactionId(String transactionId);
}
