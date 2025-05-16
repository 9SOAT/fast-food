package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.infrastructure.integration.FastFoodOrderClient;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ExternalOrderRepository implements OrderRepository {

    private final FastFoodOrderClient fastFoodOrderClient;
    private final EntityMapper entityMapper;

    public ExternalOrderRepository(FastFoodOrderClient fastFoodOrderClient, EntityMapper entityMapper) {
        this.fastFoodOrderClient = fastFoodOrderClient;
        this.entityMapper = entityMapper;
    }

    @Override
    public Order save(Order order) {
        return entityMapper.toOrder(fastFoodOrderClient.saveOrder(order));
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
