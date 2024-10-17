package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.CartItemMutation;
import com.fiap.challenge.food.application.request.CartMutation;
import com.fiap.challenge.food.application.response.CartView;
import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.ports.inbound.CartService;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public CartView create(@RequestBody CartMutation cartMutation) {
        Cart cart = cartService.create(cartMutation.consumerId());
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
