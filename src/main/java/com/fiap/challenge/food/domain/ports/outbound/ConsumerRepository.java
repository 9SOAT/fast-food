package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.PageResult;
import com.fiap.challenge.food.domain.model.consumer.Consumer;

import java.util.Optional;

public interface ConsumerRepository {

    public Consumer save(Consumer consumer);

    public Optional<Consumer> findByCpf(String cpf);

    public PageResult<Consumer> findAll(int page, int size);

    Boolean existsById(Long id);

    public Optional<Consumer> findById(Long id);
}
