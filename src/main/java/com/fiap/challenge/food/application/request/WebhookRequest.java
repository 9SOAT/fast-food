package com.fiap.challenge.food.application.request;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class WebhookRequest {

    private String transactionId;
    private WebhookAction action;
    private PaymentWebhookStatus status;
    private ZonedDateTime dateCreated;

}
