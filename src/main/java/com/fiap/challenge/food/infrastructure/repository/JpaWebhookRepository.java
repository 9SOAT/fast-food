package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.infrastructure.entity.WebhookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWebhookRepository extends JpaRepository<WebhookEntity, Long>  {
}
