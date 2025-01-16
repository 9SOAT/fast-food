package com.fiap.challenge.food.domain.model.webhook;

import com.fiap.challenge.food.application.request.WebhookAction;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Builder
public class Webhook {

    private Long paymentId;
    private WebhookAction action;
    private PaymentStatus status;
    private ZonedDateTime dateCreated;

}
