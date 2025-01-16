package com.fiap.challenge.food.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "webhook_payment")
@Builder
public class WebhookEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long paymentId;
    @Enumerated(STRING)
    private WebhookActionEntity action;
    @Enumerated(STRING)
    private PaymentStatusEntity status;
    private ZonedDateTime dateCreated;

}
