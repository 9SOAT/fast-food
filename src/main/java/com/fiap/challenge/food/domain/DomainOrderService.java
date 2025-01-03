package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.exception.UnprocessableEntityException;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.order.OrderStatus;
import com.fiap.challenge.food.domain.ports.inbound.OrderService;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Component
public class DomainOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DomainOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public PageResult<Order> getAllByStatus(List<OrderStatus> status, int page, int size) {
        return orderRepository.findAllByStatusIn(status, page, size);
    }

    @Override
    public void approvePayment(Long orderId) {
        approvePayment(
            String.format("Order not found. ID: %s", orderId),
            () -> orderRepository.findById(orderId)
        );
    }

    @Override
    public void approvePayment(String transactionId) {
        approvePayment(
            String.format("Order not found. Transaction ID: %s", transactionId),
            () -> orderRepository.findByPaymentTransactionId(transactionId)
        );
    }

    @SneakyThrows
    public void approvePayment(String notFoundMessage, Callable<Optional<Order>> getOrderAction) {
        Order order = getOrderAction.call()
            .orElseThrow(() -> new NotFoundException(notFoundMessage, "ORDER_NOT_FOUND"));

        if (!order.isWaitingPaymentStatus()) {
            throw new UnprocessableEntityException(
                String.format("Order is not waiting for payment. ID: %s", order.getId()), "ORDER_NOT_WAITING_PAYMENT");
        }
        order.approvePayment();
        orderRepository.save(order);
    }
}
