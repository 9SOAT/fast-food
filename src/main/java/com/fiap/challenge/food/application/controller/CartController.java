package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.CartItemMutation;
import com.fiap.challenge.food.application.response.CartView;
import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.ports.inbound.CartService;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import com.fiap.challenge.food.infrastructure.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final ViewMapper viewMapper;

    public CartController(CartService cartService, ViewMapper viewMapper) {
        this.viewMapper = viewMapper;
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public CartView create(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long consumerId = JwtUtil.extractConsumerIdFromToken(authHeader);
        Cart cart = cartService.create(consumerId);
        return viewMapper.toCartView(cart);
    }

    @GetMapping("/{id}")
    public CartView get(@PathVariable Long id) {
        Cart cart = cartService.getById(id);
        return viewMapper.toCartView(cart);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{cartId}/items")
    public CartView createItem(@PathVariable Long cartId, @Valid @RequestBody CartItemMutation itemMutation) {
        Cart cart = cartService.addItem(cartId, itemMutation.productId(), itemMutation.quantity());
        return viewMapper.toCartView(cart);
    }
}
