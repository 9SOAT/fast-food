package com.fiap.challenge.food.application.request;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
public class MercadoPagoWebhookRequest {

    private Long id;
    private String action;
    private String api_version;
    private Map<String, String> data;
    private ZonedDateTime date_created;
    private boolean live_mode;
    private String type;
    private long user_id;

}
