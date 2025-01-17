package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.PaymentWebhookStatus;
import com.fiap.challenge.food.application.request.WebhookRequest;
import com.fiap.challenge.food.application.request.WebhookAction;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.inbound.WebhookService;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WebhookControllerTest {

    @Mock
    private WebhookService webhookService;

    @Mock
    private ViewMapper viewMapper;

    @InjectMocks
    private WebhookController webhookController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(webhookController).build();
    }

    @Test
    void sendWebhookNotificationShouldUpdatePayment() throws Exception {
        when(viewMapper.toWebhook(any())).thenReturn(Webhook.builder().build());

        WebhookRequest webhookRequest = new WebhookRequest();
        webhookRequest.setPaymentId(1L);
        webhookRequest.setAction(WebhookAction.UPDATED);
        webhookRequest.setStatus(PaymentWebhookStatus.APPROVED);
        webhookRequest.setDateCreated(ZonedDateTime.parse("2025-01-16T12:34:56Z"));

        String requestBody = """
        {
            "paymentId": 1,
            "action": "UPDATED",
            "status": "APPROVED",
            "dateCreated": "2025-01-16T12:34:56Z"
        }
        """;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/webhook/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        resultActions.andExpect(status().isCreated());
        verify(viewMapper, times(1)).toWebhook(webhookRequest);
        verify(webhookService, times(1)).updatePayment(any(Webhook.class));
    }

}
