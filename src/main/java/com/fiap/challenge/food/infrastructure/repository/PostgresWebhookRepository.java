package com.fiap.challenge.food.infrastructure.repository;

import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;

import com.fiap.challenge.food.infrastructure.entity.WebhookEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class PostgresWebhookRepository implements WebhookRepository {

    private final JpaWebhookRepository jpaWebhookRepository;
    private final EntityMapper entityMapper;

    public PostgresWebhookRepository(JpaWebhookRepository jpaWebhookRepository, EntityMapper entityMapper) {
        this.jpaWebhookRepository = jpaWebhookRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Webhook save(Webhook webHook) {
        WebhookEntity saved = jpaWebhookRepository.save(entityMapper.toWebhookEntity(webHook));
        return entityMapper.toWebhook(saved);
    }
}
