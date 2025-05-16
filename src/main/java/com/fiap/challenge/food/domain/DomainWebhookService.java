package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.inbound.WebhookService;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import org.springframework.stereotype.Component;

@Component
public class DomainWebhookService implements WebhookService {

    private final WebhookRepository webhookRepository;
    private final OrderRepository orderRepository;

    public DomainWebhookService(WebhookRepository webhookRepository, OrderRepository orderRepository) {
        this.webhookRepository = webhookRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void updatePayment(Webhook webhook) {
        if(PaymentStatus.APPROVED.equals(webhook.getStatus())) {
            orderRepository.approvePayment(webhook.getTransactionId());
        } else {
            orderRepository.rejectPayment(webhook.getTransactionId());
        }
        webhookRepository.save(webhook);
    }
}
