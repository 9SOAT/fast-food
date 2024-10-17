package com.fiap.challenge.food.infrastructure.integration;

import com.fiap.challenge.food.domain.model.exception.UnprocessableEntityException;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.ports.outbound.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import static com.fiap.challenge.food.domain.model.payment.PaymentStatus.PENDING;
import static com.fiap.challenge.food.domain.model.payment.PaymentType.PIX;

@Log4j2
@Component
public class PaymentClientAdapter implements PaymentClient {

    private final MercadoPagoClient mercadoPagoClient;

    public PaymentClientAdapter(MercadoPagoClient mercadoPagoClient) {
        this.mercadoPagoClient = mercadoPagoClient;
    }

    public Payment create(Payment payment) {
        log.info("Creating fake payment. amount={}", payment.getAmount());
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setQrCode("https://fake.qr.code");
        payment.setStatus(PENDING);
        payment.setType(PIX);
        return payment;
    }

    public Payment NonFake(Payment payment) {
        String idempotencyKey = UUID.randomUUID().toString();
        com.mercadopago.resources.payment.Payment mercadoPagoPayment = createMercadoPagoPayment(idempotencyKey, payment.getAmount());
        payment.setQrCode(mercadoPagoPayment.getPointOfInteraction().getTransactionData().getQrCode());
        payment.setTransactionId(mercadoPagoPayment.getTransactionDetails().getTransactionId());
        return payment;
    }


    private com.mercadopago.resources.payment.Payment createMercadoPagoPayment(String idempotencyKey, BigDecimal paymentAmount) {
        PaymentCreateRequest paymentCreateRequest = buildMercadoPagoPayment(paymentAmount);
        MPRequestOptions requestOptions = buildRequestOptions(idempotencyKey);

        try {
            return mercadoPagoClient.create(paymentCreateRequest, requestOptions);
        } catch (MPException | MPApiException e) {
            log.error("Error creating payment", e);
            throw new UnprocessableEntityException("Error creating payment", "PAYMENT_ERROR");
        }
    }

    private static MPRequestOptions buildRequestOptions(String idempotencyKey) {
        return MPRequestOptions.builder()
            .customHeaders(Map.of("x-idempotency-key", idempotencyKey))
            .build();
    }

    private static PaymentCreateRequest buildMercadoPagoPayment(BigDecimal paymentAmount) {
        return PaymentCreateRequest.builder()
            .transactionAmount(paymentAmount)
            .description("Compra no Food Challenge")
            .paymentMethodId("pix")
            .dateOfExpiration(OffsetDateTime.from(OffsetDateTime.from(LocalDateTime.now().plusMinutes(1))))
            .build();
    }
}
