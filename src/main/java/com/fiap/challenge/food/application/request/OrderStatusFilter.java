package com.fiap.challenge.food.application.request;

import com.fiap.challenge.food.domain.model.order.OrderStatus;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum OrderStatusFilter {
    WAITING_PAYMENT(OrderStatus.WAITING_PAYMENT),
    READY_FOR_PREPARATION(OrderStatus.READY_FOR_PREPARATION),
    IN_PREPARATION(OrderStatus.IN_PREPARATION),
    READY_FOR_PICKUP(OrderStatus.READY_FOR_PICKUP),
    FINISHED(OrderStatus.FINISHED),
    IN_PROGRESS(List.of(
        OrderStatus.READY_FOR_PREPARATION,
        OrderStatus.IN_PREPARATION,
        OrderStatus.READY_FOR_PICKUP
    )),
    ALL(Arrays.stream(OrderStatus.values()).toList());

    private final List<OrderStatus> orderStatuses;

    private OrderStatusFilter(OrderStatus orderStatus) {
        this.orderStatuses = List.of(orderStatus);
    }

    private OrderStatusFilter(List<OrderStatus> orderStatuses) {
        this.orderStatuses = orderStatuses;
    }


}
