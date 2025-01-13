package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.inbound.WebhookService;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import org.springframework.stereotype.Component;

@Component
public class DomainWebhookService implements WebhookService {

    private final WebhookRepository webhookRepository;

    public DomainWebhookService(WebhookRepository webhookRepository) {
        this.webhookRepository = webhookRepository;
    }

    @Override
    public void saveNotification(Webhook webhook) {
        webhookRepository.save(webhook);
    }
}
