package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.infrastructure.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartRepository extends JpaRepository<CartEntity, Long> {
}
