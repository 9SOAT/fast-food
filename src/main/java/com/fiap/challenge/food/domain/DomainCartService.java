package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.model.cart.CartItem;
import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.exception.UnprocessableEntityException;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.ports.inbound.CartService;
import com.fiap.challenge.food.domain.ports.outbound.CartRepository;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainCartService implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public DomainCartService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart create(Long consumerId) {
        log.info("Creating cart for consumerId={}", consumerId);
        return cartRepository.save(new Cart(consumerId));
    }

    @Override
    public Cart getById(Long id) {
        return cartRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Cart not found id=%d", id), "CART_NOT_FOUND"));
    }

    @Override
    public Cart addItem(Long cartId, Long productId, int quantity) {

        Product product = getProduct(productId);
        Cart cart = getById(cartId);

        cart.getLatestItemCategory().ifPresent(latestCategory -> {
            if (!product.getCategory().isSubsequent(latestCategory)) {
                log.warn("Invalid addition sequence of items. cartId={} productId={} latestCategory={} productCategory={}",
                    cartId, productId, latestCategory, product.getCategory());
                throw new UnprocessableEntityException("Invalid addition sequence of items", "INVALID_ITEM_SEQUENCE");
            }
        });

        CartItem cartItem = CartItem.builder()
            .category(product.getCategory())
            .productName(product.getName())
            .productId(product.getId())
            .price(product.getPrice())
            .quantity(quantity)
            .build();

        return cartRepository.save(cart.addItem(cartItem));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException(String.format("Product not found %d", productId), "PRODUCT_NOT_FOUND"));
    }
}
