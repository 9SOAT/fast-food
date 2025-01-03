package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.infrastructure.entity.OrderEntity;
import com.fiap.challenge.food.infrastructure.entity.OrderStatusEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findAllByStatusIn(Collection<@NotNull OrderStatusEntity> statuses, Pageable pageable);
    Optional<OrderEntity> findByPaymentTransactionId(String transactionId);
    @Query("SELECT o FROM OrderEntity o WHERE o.status IN :statuses " +
        "ORDER BY CASE " +
        "WHEN o.status = 'READY_FOR_PICKUP' THEN 1 " +
        "WHEN o.status = 'IN_PREPARATION' THEN 2 " +
        "WHEN o.status = 'FINISHED' THEN 3 " +
        "END ASC, o.createdAt ASC")
    Page<OrderEntity> findAllByStatusInOrderByCustom(
        @Param("statuses") List<OrderStatusEntity> statuses,
        Pageable pageable
    );
}
