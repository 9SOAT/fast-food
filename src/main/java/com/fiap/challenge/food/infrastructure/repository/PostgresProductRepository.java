package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductCategory;
import com.fiap.challenge.food.domain.model.product.ProductStatus;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import com.fiap.challenge.food.infrastructure.entity.ProductCategoryEntity;
import com.fiap.challenge.food.infrastructure.entity.ProductEntity;
import com.fiap.challenge.food.infrastructure.entity.ProductStatusEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import com.fiap.challenge.food.infrastructure.mapper.PageResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PostgresProductRepository implements ProductRepository {

    public static final Sort SORT_BY_NAME_ASC = Sort.by(Sort.Order.asc("name"));

    private final JpaProductRepository jpaProductRepository;
    private final EntityMapper entityMapper;

    public PostgresProductRepository(JpaProductRepository jpaProductRepository, EntityMapper entityMapper) {
        this.jpaProductRepository = jpaProductRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public PageResult<Product> findAllByCategory(ProductCategory category, int page, int size) {
        ProductCategoryEntity categoryEntity = entityMapper.toCategoryEntity(category);
        Page<ProductEntity> allByCategory = jpaProductRepository.findAllByCategoryAndStatus(categoryEntity, ProductStatusEntity.ACTIVE,
            PageRequest.of(Math.max(page - 1, 0), size, SORT_BY_NAME_ASC));
        return PageResultMapper.toPageResult(allByCategory, entityMapper::toProduct);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository.findById(id).map(entityMapper::toProduct);
    }

    @Override
    public Product save(Product product) {
        ProductEntity saved = jpaProductRepository.save(entityMapper.toProductEntity(product));
        return entityMapper.toProduct(saved);
    }

    @Override
    public PageResult<Product> findAllByStatus(List<ProductStatus> statuses, int page, int size) {
        List<ProductStatusEntity> statusEntities = statuses.stream().map(entityMapper::toStatusEntity).toList();
        Page<ProductEntity> products = jpaProductRepository.findAllByStatusIn(statusEntities,
            PageRequest.of(Math.max(page - 1, 0), size, SORT_BY_NAME_ASC));
        return PageResultMapper.toPageResult(products, entityMapper::toProduct);
    }
}
