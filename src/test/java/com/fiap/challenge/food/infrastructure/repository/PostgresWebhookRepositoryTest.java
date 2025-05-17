package com.fiap.challenge.food.infrastructure.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.infrastructure.entity.WebhookEntity;
import com.fiap.challenge.food.infrastructure.mapper.EntityMapper;

class PostgresWebhookRepositoryTest {

    private JpaWebhookRepository jpaWebhookRepository;
    private EntityMapper entityMapper;
    private PostgresWebhookRepository repository;

    @BeforeEach
    void setUp() {
        jpaWebhookRepository = mock(JpaWebhookRepository.class);
        entityMapper = mock(EntityMapper.class);
        repository = new PostgresWebhookRepository(jpaWebhookRepository, entityMapper);
    }

    @Test
    @DisplayName("Deve salvar e retornar o webhook corretamente")
    void saveShouldSaveAndReturnWebhook() {
        Webhook webhook = mock(Webhook.class);
        WebhookEntity entity = mock(WebhookEntity.class);
        WebhookEntity savedEntity = mock(WebhookEntity.class);
        Webhook expectedWebhook = mock(Webhook.class);

        when(entityMapper.toWebhookEntity(webhook)).thenReturn(entity);
        when(jpaWebhookRepository.save(entity)).thenReturn(savedEntity);
        when(entityMapper.toWebhook(savedEntity)).thenReturn(expectedWebhook);

        Webhook result = repository.save(webhook);

        assertEquals(expectedWebhook, result);
        verify(jpaWebhookRepository).save(entity);
    }
}
