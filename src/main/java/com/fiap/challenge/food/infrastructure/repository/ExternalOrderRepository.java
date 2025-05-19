package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.infrastructure.entity.OrderEntity;
import com.fiap.challenge.food.infrastructure.integration.FastFoodOrderClient;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExternalOrderRepository implements OrderRepository {

    private final FastFoodOrderClient fastFoodOrderClient;
    private final EntityMapper entityMapper;

    public ExternalOrderRepository(FastFoodOrderClient fastFoodOrderClient, EntityMapper entityMapper) {
        this.fastFoodOrderClient = fastFoodOrderClient;
        this.entityMapper = entityMapper;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = fastFoodOrderClient.saveOrder(order);
        log.info("Order saved: {}", orderEntity.getId());

        return entityMapper.toOrder(orderEntity);
    }

    @Override
    public void approvePayment(String transactionId) {
        fastFoodOrderClient.approvePayment(transactionId);
    }

    @Override
    public void rejectPayment(String transactionId) {
        fastFoodOrderClient.rejectPayment(transactionId);
    }
}
