package com.fiap.challenge.food.domain.model.webhook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Webhook {

    private Long id;
    private String action;
    private String apiVersion;
    private Map<String, String> data;
    private ZonedDateTime dateCreated;
    private boolean liveMode;
    private String type;
    private long userId;

}
