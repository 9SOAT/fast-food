package com.fiap.challenge.food.infrastructure.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderEntityTest {

    @Test
    void orderEntityRemoveItem() {
        OrderEntity order = new OrderEntity();
        OrderItemEntity item = new OrderItemEntity();
        order.setItems(List.of(item));
        assertNotNull(item.getOrder());
        assertNotNull(order.getItems());
        assertFalse(order.getItems().isEmpty());
    }

    @Test
    void setPayment() {
        OrderEntity order = new OrderEntity();
        PaymentEntity payment = new PaymentEntity();
        order.setPayment(payment);

        assertNotNull(order.getPayment());
        assertNotNull(payment.getOrder());
    }
}
