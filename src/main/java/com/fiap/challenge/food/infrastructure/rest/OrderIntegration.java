package com.fiap.challenge.food.infrastructure.rest;

import com.fiap.challenge.food.domain.model.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderIntegration {

    @Value(value = "${order.base-url}")
    private String baseUrl;

    @Value(value = "${order.approve-payment}")
    private String approvePayment;

    @Value(value = "${order.reject-payment}")
    private String rejectPayment;

    @Value(value = "${order.save-order}")
    private String saveOrder;

    private final RestTemplate restTemplate;

    public void approvePayment(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        restTemplate.postForEntity(baseUrl + id + approvePayment, entity, Void.class);
    }

    public void rejectPayment(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        restTemplate.postForEntity(baseUrl + id + rejectPayment, entity, Void.class);
    }

    public Order saveOrder(Order order) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Order> entity = new HttpEntity<>(order, headers);

        ResponseEntity<Order> response = restTemplate.postForEntity(baseUrl + saveOrder, entity, Order.class);

        return response.getBody();
    }

}
