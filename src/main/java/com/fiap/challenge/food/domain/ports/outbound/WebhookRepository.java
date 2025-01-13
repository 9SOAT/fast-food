package com.fiap.challenge.food.domain.ports.outbound;

import com.fiap.challenge.food.domain.model.webhook.Webhook;

public interface WebhookRepository {

    public Webhook save(Webhook webHook);

}
