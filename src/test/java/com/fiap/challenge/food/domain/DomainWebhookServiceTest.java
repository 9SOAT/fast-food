package com.fiap.challenge.food.domain;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fiap.challenge.food.domain.model.exception.NotFoundException;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.ports.outbound.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;

import java.util.Optional;

class DomainWebhookServiceTest {

    private WebhookRepository webhookRepository;
    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;
    private DomainWebhookService service;

    @BeforeEach
    void setUp() {
        webhookRepository = mock(WebhookRepository.class);
        orderRepository = mock(OrderRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        service = new DomainWebhookService(webhookRepository, orderRepository, paymentRepository);
    }

    @Test
    @DisplayName("Deve aprovar pagamento quando status for APPROVED e salvar webhook")
    void updatePaymentWithApprovedStatusApprovesPaymentAndSavesWebhook() {
        Webhook webhook = mock(Webhook.class);
        when(webhook.getStatus()).thenReturn(PaymentStatus.APPROVED);
        when(webhook.getTransactionId()).thenReturn("tx-123");
        Payment payment = mock(Payment.class);
        when(payment.getId()).thenReturn(1L);
        when(paymentRepository.findByTransactionId("tx-123")).thenReturn(Optional.of(payment));
        service.updatePayment(webhook);

        verify(orderRepository).approvePayment("1");
        verify(paymentRepository).save(payment);
        verify(webhookRepository).save(webhook);
        verify(orderRepository, never()).rejectPayment(anyString());
    }

    @Test
    @DisplayName("Deve rejeitar pagamento quando status não for APPROVED e salvar webhook")
    void updatePaymentWithNonApprovedStatusRejectsPaymentAndSavesWebhook() {
        Webhook webhook = mock(Webhook.class);
        when(webhook.getStatus()).thenReturn(PaymentStatus.REJECTED);
        when(webhook.getTransactionId()).thenReturn("tx-456");
        Payment payment = mock(Payment.class);
        when(payment.getId()).thenReturn(1L);
        when(paymentRepository.findByTransactionId("tx-456")).thenReturn(Optional.of(payment));

        service.updatePayment(webhook);

        verify(orderRepository).rejectPayment("1");
        verify(webhookRepository).save(webhook);
        verify(paymentRepository).save(payment);

        verify(orderRepository, never()).approvePayment(anyString());
    }

    @Test
    @DisplayName("Deve lançar NullPointerException se webhook for nulo")
    void updatePaymentWithNullWebhookThrowsException() {
        assertThrows(NullPointerException.class, () -> service.updatePayment(null));
    }

    @Test
    @DisplayName("Deve lançar NotFoundException se pagamento não for encontrado")
    void updatePaymentWithNonExistentTransactionIdThrowsException() {
        Webhook webhook = mock(Webhook.class);
        when(webhook.getStatus()).thenReturn(PaymentStatus.APPROVED);
        when(webhook.getTransactionId()).thenReturn("tx-789");
        when(paymentRepository.findByTransactionId("tx-789")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.updatePayment(webhook));
    }
}
