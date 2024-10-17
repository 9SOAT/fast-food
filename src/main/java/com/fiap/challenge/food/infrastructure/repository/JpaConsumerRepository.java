package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.infrastructure.entity.ConsumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaConsumerRepository extends JpaRepository<ConsumerEntity, Long> {

    Optional<ConsumerEntity> findByCpf(String cpf);

}
