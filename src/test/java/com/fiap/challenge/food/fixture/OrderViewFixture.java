package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.application.response.OrderStatusView;
import com.fiap.challenge.food.application.response.OrderView;

import java.math.BigDecimal;
import java.util.List;

public class OrderViewFixture {

    public static OrderView validOrderView() {
        return new OrderView(
            1L,
            1L,
            1L,
            PaymentViewFixture.validPaymentView(),
            List.of(OrderItemViewFixture.validItem()),
            OrderStatusView.READY_FOR_PREPARATION,
            BigDecimal.valueOf(100),
            5L
        );
    }
}
