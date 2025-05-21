package com.fiap.challenge.food.infrastructure.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    public ExternalOrderRepository(FastFoodOrderClient fastFoodOrderClient, EntityMapper entityMapper, ObjectMapper objectMapper) {
        this.fastFoodOrderClient = fastFoodOrderClient;
        this.entityMapper = entityMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = fastFoodOrderClient.saveOrder(order);
        log.info("Order saved: {}", orderEntity.getId());
        orderEntity.setPayment(entityMapper.toPaymentEntity(order.getPayment()));
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
