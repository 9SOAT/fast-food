package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.inbound.WebhookService;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import com.fiap.challenge.food.infrastructure.restProvider.OrderProvider;
import org.springframework.stereotype.Component;

@Component
public class DomainWebhookService implements WebhookService {

    private final WebhookRepository webhookRepository;
    private final OrderProvider orderProvider;

    public DomainWebhookService(WebhookRepository webhookRepository, OrderProvider orderProvider) {
        this.webhookRepository = webhookRepository;
        this.orderProvider = orderProvider;
    }

    @Override
    public void updatePayment(Webhook webhook) {
        if (PaymentStatus.APPROVED.equals(webhook.getStatus())) {
            orderProvider.approvePayment(Long.valueOf(webhook.getTransactionId()));
        } else {
            orderProvider.rejectPayment(webhook.getTransactionId());
        }
        webhookRepository.save(webhook);
    }
}
