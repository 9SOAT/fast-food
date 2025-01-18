package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.inbound.OrderService;
import com.fiap.challenge.food.domain.ports.inbound.WebhookService;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import org.springframework.stereotype.Component;

@Component
public class DomainWebhookService implements WebhookService {

    private final WebhookRepository webhookRepository;
    private final OrderService orderService;

    public DomainWebhookService(WebhookRepository webhookRepository, OrderService orderService) {
        this.webhookRepository = webhookRepository;
        this.orderService = orderService;
    }

    @Override
    public void updatePayment(Webhook webhook) {
        if(PaymentStatus.APPROVED.equals(webhook.getStatus())) {
            orderService.approvePayment(webhook.getTransactionId());
        } else {
            orderService.rejectPayment(webhook.getTransactionId());
        }
        webhookRepository.save(webhook);
    }
}
