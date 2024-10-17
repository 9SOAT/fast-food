package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.consumer.Consumer;
import com.fiap.challenge.food.domain.ports.outbound.ConsumerRepository;
import com.fiap.challenge.food.infrastructure.entity.ConsumerEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import com.fiap.challenge.food.infrastructure.mapper.PageResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostgresConsumerRepository implements ConsumerRepository {

    public static final Sort SORT_BY_NAME_ORDER_ASC = Sort.by(Sort.Order.asc("name"));
    private final JpaConsumerRepository jpaConsumerRepository;
    private final EntityMapper entityMapper;

    public PostgresConsumerRepository(JpaConsumerRepository jpaConsumerRepository, EntityMapper entityMapper) {
        this.jpaConsumerRepository = jpaConsumerRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Consumer save(Consumer consumer) {
        ConsumerEntity consumerEntity = entityMapper.toConsumerEntity(consumer);
        return entityMapper.toConsumer(jpaConsumerRepository.save(consumerEntity));
    }

    @Override
    public Optional<Consumer> findByCpf(String cpf) {
        return jpaConsumerRepository.findByCpf(cpf)
            .map(entityMapper::toConsumer);
    }

    @Override
    public PageResult<Consumer> findAll(int page, int size) {
        Page<ConsumerEntity> consumers = jpaConsumerRepository.findAll(PageRequest.of(Math.max(page - 1, 0), size, SORT_BY_NAME_ORDER_ASC));
        return PageResultMapper.toPageResult(consumers, entityMapper::toConsumer);
    }

    @Override
    public Boolean existsById(Long id) {
        return jpaConsumerRepository.existsById(id);
    }

    @Override
    public Optional<Consumer> findById(Long id) {
        return jpaConsumerRepository.findById(id)
            .map(entityMapper::toConsumer);
    }
}
