package com.fiap.challenge.food.domain;

import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.inbound.WebhookService;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.domain.ports.outbound.PaymentRepository;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import org.springframework.stereotype.Component;

@Component
public class DomainWebhookService implements WebhookService {

    private final WebhookRepository webhookRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public DomainWebhookService(WebhookRepository webhookRepository, OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.webhookRepository = webhookRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void updatePayment(Webhook webhook) {
        Payment payment = getPayment(webhook.getTransactionId());

        if (PaymentStatus.APPROVED.equals(webhook.getStatus())) {
            payment.approve();
            paymentRepository.save(payment);
            orderRepository.approvePayment(payment.getId().toString());
        } else {
            payment.reject();
            paymentRepository.save(payment);
            orderRepository.rejectPayment(payment.getId().toString());
        }
        webhookRepository.save(webhook);
    }

    private Payment getPayment(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
            .orElseThrow(() -> new NotFoundException("Payment not found", "PAYMENT_NOT_FOUND"));
    }
}
