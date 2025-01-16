package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.inbound.WebhookService;
import com.fiap.challenge.food.domain.ports.outbound.PaymentClient;
import com.fiap.challenge.food.domain.ports.outbound.PaymentRepository;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import org.springframework.stereotype.Component;

@Component
public class DomainWebhookService implements WebhookService {

    private final WebhookRepository webhookRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentClient paymentClient;

    public DomainWebhookService(WebhookRepository webhookRepository, PaymentRepository paymentRepository, PaymentClient paymentClient) {
        this.webhookRepository = webhookRepository;
        this.paymentClient = paymentClient;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void updatePayment(Webhook webhook) {
        Payment payment = paymentRepository.findById(webhook.getPaymentId())
            .orElseThrow(() -> new NotFoundException(String.format("Payment not found id=%d", webhook.getPaymentId()), "PAYMENT_NOT_FOUND"));

        Payment paymentUpdated = paymentClient.update(payment, webhook.getStatus());
        paymentRepository.save(paymentUpdated);
        webhookRepository.save(webhook);
    }
}
