package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.OrderStatusFilter;
import com.fiap.challenge.food.application.response.OrderView;
import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.ports.inbound.OrderService;
import com.fiap.challenge.food.infrastructure.mapper.PageResultMapper;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fiap.challenge.food.domain.model.order.OrderStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ViewMapper viewMapper;

    public OrderController(OrderService orderService, ViewMapper viewMapper) {
        this.orderService = orderService;
        this.viewMapper = viewMapper;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{id}/payment/approval")
    public void approvePayment(@PathVariable Long id) {
        orderService.approvePayment(id);
    }

    @GetMapping()
    public PageResult<OrderView> getOrdersByStatus(
        @RequestParam @Min(1) int page,
        @Max(20) @RequestParam int size,
        @RequestParam(name = "status") OrderStatusFilter statusFilter
    ) {
        PageResult<Order> orderPage = orderService.getAllByStatus(statusFilter.getOrderStatuses(), page, size);
        return PageResultMapper.transformContent(orderPage, viewMapper::toOrderView);
    }

    @GetMapping("/list")
    public PageResult<OrderView> getOrders(
        @RequestParam @Min(1) int page,
        @Max(20) @RequestParam int size
    ) {
        PageResult<Order> orderPage = orderService.getAllByStatusInOrderByCreatedAt(List.of(IN_PREPARATION, READY_FOR_PICKUP, FINISHED), page, size);
        return PageResultMapper.transformContent(orderPage, viewMapper::toOrderView);
    }

    @Transactional
    @PatchMapping("/{id}/status/transition")
    public void updateOrderStatus(@PathVariable Long id) {
        orderService.updateStatus(id);
    }
}
