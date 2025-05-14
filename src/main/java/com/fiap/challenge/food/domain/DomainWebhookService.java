package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.inbound.WebhookService;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import com.fiap.challenge.food.infrastructure.rest.OrderIntegration;
import org.springframework.stereotype.Component;

@Component
public class DomainWebhookService implements WebhookService {

    private final WebhookRepository webhookRepository;
    private final OrderIntegration orderIntegration;

    public DomainWebhookService(WebhookRepository webhookRepository, OrderIntegration orderIntegration) {
        this.webhookRepository = webhookRepository;
        this.orderIntegration = orderIntegration;
    }

    @Override
    public void updatePayment(Webhook webhook) {
        if (PaymentStatus.APPROVED.equals(webhook.getStatus())) {
            orderIntegration.approvePayment(Long.valueOf(webhook.getTransactionId()));
        } else {
            orderIntegration.rejectPayment(webhook.getTransactionId());
        }
        webhookRepository.save(webhook);
    }
}
