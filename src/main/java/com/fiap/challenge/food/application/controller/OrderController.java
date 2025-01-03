package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.OrderStatusFilter;
import com.fiap.challenge.food.application.response.OrderView;
import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.ports.inbound.OrderService;
import com.fiap.challenge.food.infrastructure.mapper.PageResultMapper;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
