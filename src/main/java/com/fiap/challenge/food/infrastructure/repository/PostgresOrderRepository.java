package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.order.OrderStatus;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.infrastructure.entity.OrderEntity;
import com.fiap.challenge.food.infrastructure.entity.OrderStatusEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import com.fiap.challenge.food.infrastructure.mapper.PageResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PostgresOrderRepository implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final EntityMapper entityMapper;

    public PostgresOrderRepository(JpaOrderRepository jpaOrderRepository, EntityMapper entityMapper) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = entityMapper.toOrderEntity(order);
        return entityMapper.toOrder(jpaOrderRepository.save(orderEntity));
    }

    @Override
    public PageResult<Order> findAllByStatusIn(List<OrderStatus> inProgressStatuses, int page, int size) {
        List<OrderStatusEntity> statusEntities = inProgressStatuses.stream()
            .map(entityMapper::toOrderStatusEntity).toList();

        Page<OrderEntity> orderEntities = jpaOrderRepository.findAllByStatusIn(statusEntities,
            PageRequest.of(Math.max(page - 1, 0), size, Sort.by(Sort.Order.desc("id"))));

        return PageResultMapper.toPageResult(orderEntities, entityMapper::toOrder);
    }

    @Override
    public PageResult<Order> findAllByStatusInOrderByCreatedAt(List<OrderStatus> inProgressStatuses, int page, int size) {
        List<OrderStatusEntity> statusEntities = inProgressStatuses.stream()
            .map(entityMapper::toOrderStatusEntity).toList();

        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
        Page<OrderEntity> orderEntities = jpaOrderRepository.findAllByStatusInOrderByCustom(statusEntities, pageable);

        return PageResultMapper.toPageResult(orderEntities, entityMapper::toOrder);
    }

    @Override
    public Optional<Order> findByPaymentTransactionId(String transactionId) {
        return jpaOrderRepository.findByPaymentTransactionId(transactionId)
            .map(entityMapper::toOrder);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaOrderRepository.findById(id)
            .map(entityMapper::toOrder);
    }
}
