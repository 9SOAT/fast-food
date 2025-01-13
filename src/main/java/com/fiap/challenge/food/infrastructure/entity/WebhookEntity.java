package com.fiap.challenge.food.infrastructure.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "webhook_payment")
@Builder
public class WebhookEntity {

    @Id
    private Long id;
    private String action;
    @Type(JsonBinaryType.class)
    private Map<String, String> data;
    private String apiVersion;
    private ZonedDateTime dateCreated;
    private boolean liveMode;
    private String type;
    private Long userId;

}
