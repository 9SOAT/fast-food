package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.CheckoutMutation;
import com.fiap.challenge.food.application.response.OrderView;
import com.fiap.challenge.food.domain.ports.inbound.CheckoutService;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final ViewMapper viewMapper;

    public CheckoutController(CheckoutService checkoutService, ViewMapper viewMapper) {
        this.checkoutService = checkoutService;
        this.viewMapper = viewMapper;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public OrderView checkout(@Valid @RequestBody CheckoutMutation checkoutMutation) {
        return viewMapper.toOrderView(checkoutService.checkout(checkoutMutation.cartId()));
    }
}
