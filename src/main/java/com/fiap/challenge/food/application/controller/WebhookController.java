package com.fiap.challenge.food.application.controller;

import com.fiap.challenge.food.application.request.WebhookRequest;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.domain.ports.inbound.WebhookService;
import com.fiap.challenge.food.infrastructure.mapper.ViewMapper;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final WebhookService webhookService;
    private final ViewMapper viewMapper;

    public WebhookController(WebhookService webhookService, ViewMapper viewMapper) {
        this.webhookService = webhookService;
        this.viewMapper = viewMapper;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public void webhookNotification(@RequestBody WebhookRequest webhookRequest) {
        Webhook webhook = viewMapper.toWebhook(webhookRequest);
        webhookService.updatePayment(webhook);
    }

}
