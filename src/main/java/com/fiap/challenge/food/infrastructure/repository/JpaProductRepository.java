package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.infrastructure.entity.ProductCategoryEntity;
import com.fiap.challenge.food.infrastructure.entity.ProductEntity;
import com.fiap.challenge.food.infrastructure.entity.ProductStatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findAllByCategoryAndStatus(ProductCategoryEntity category, ProductStatusEntity status, Pageable pageable);

    Page<ProductEntity> findAllByStatusIn(List<ProductStatusEntity> productStatusEntity, Pageable pageable);
}
