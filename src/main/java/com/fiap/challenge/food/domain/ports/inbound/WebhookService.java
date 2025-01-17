package com.fiap.challenge.food.domain.ports.inbound;

import com.fiap.challenge.food.domain.model.webhook.Webhook;

public interface WebhookService {

    public void updatePayment(Webhook webhook);

}
