package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.order.OrderItem;
import com.fiap.challenge.food.domain.model.order.OrderStatus;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.payment.PaymentType;
import com.fiap.challenge.food.domain.model.product.ProductCategory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class OrderFixture {

    public static Order validOrder() {
        return new Order(
            1L,
            1L,
            "08444331015",
            new Payment(
                null,
                null,
                PaymentType.PIX,
                new BigDecimal("10"),
                UUID.randomUUID().toString(),
                PaymentStatus.PENDING,
                null
            ),
            List.of(new OrderItem(1L, 1L, "X-Tudo", ProductCategory.SANDWICH, 2, new BigDecimal("20.00"), new BigDecimal("40.00"))), OrderStatus.WAITING_PAYMENT, new BigDecimal("40.00"),
            Instant.now()
        );
    }

    public static PageResult<Order> pageResultWithOneOrder() {
        return new PageResult<>(List.of(validOrder()), 1,1,1,1);
    }
}
