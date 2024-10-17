package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.ports.outbound.CartRepository;
import com.fiap.challenge.food.infrastructure.entity.CartEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostgresCartRepository implements CartRepository {

    private final JpaCartRepository jpaCartRepository;
    private final EntityMapper entityMapper;

    public PostgresCartRepository(JpaCartRepository jpaCartRepository, EntityMapper entityMapper) {
        this.jpaCartRepository = jpaCartRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return jpaCartRepository.findById(id)
                .map(entityMapper::toCart);
    }

    @Override
    public Cart save(Cart cart) {
        CartEntity saved = jpaCartRepository.save(entityMapper.toCartEntity(cart));
        return entityMapper.toCart(saved);
    }
}
