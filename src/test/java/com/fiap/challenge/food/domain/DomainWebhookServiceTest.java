package com.fiap.challenge.food.domain;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;

class DomainWebhookServiceTest {

    private WebhookRepository webhookRepository;
    private OrderRepository orderRepository;
    private DomainWebhookService service;

    @BeforeEach
    void setUp() {
        webhookRepository = mock(WebhookRepository.class);
        orderRepository = mock(OrderRepository.class);
        service = new DomainWebhookService(webhookRepository, orderRepository);
    }

    @Test
    @DisplayName("Deve aprovar pagamento quando status for APPROVED e salvar webhook")
    void updatePaymentWithApprovedStatusApprovesPaymentAndSavesWebhook() {
        Webhook webhook = mock(Webhook.class);
        when(webhook.getStatus()).thenReturn(PaymentStatus.APPROVED);
        when(webhook.getTransactionId()).thenReturn("tx-123");

        service.updatePayment(webhook);

        verify(orderRepository).approvePayment("tx-123");
        verify(webhookRepository).save(webhook);
        verify(orderRepository, never()).rejectPayment(anyString());
    }

    @Test
    @DisplayName("Deve rejeitar pagamento quando status não for APPROVED e salvar webhook")
    void updatePaymentWithNonApprovedStatusRejectsPaymentAndSavesWebhook() {
        Webhook webhook = mock(Webhook.class);
        when(webhook.getStatus()).thenReturn(PaymentStatus.REJECTED);
        when(webhook.getTransactionId()).thenReturn("tx-456");

        service.updatePayment(webhook);

        verify(orderRepository).rejectPayment("tx-456");
        verify(webhookRepository).save(webhook);
        verify(orderRepository, never()).approvePayment(anyString());
    }

    @Test
    @DisplayName("Deve lançar NullPointerException se webhook for nulo")
    void updatePaymentWithNullWebhookThrowsException() {
        assertThrows(NullPointerException.class, () -> service.updatePayment(null));
    }
}
