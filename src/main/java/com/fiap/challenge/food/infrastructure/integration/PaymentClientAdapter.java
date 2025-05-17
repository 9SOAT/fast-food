package com.fiap.challenge.food.infrastructure.integration;

import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.payment.PaymentStatus;
import com.fiap.challenge.food.domain.ports.outbound.PaymentClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.fiap.challenge.food.domain.model.payment.PaymentStatus.PENDING;
import static com.fiap.challenge.food.domain.model.payment.PaymentType.PIX;

@Log4j2
@Component
public class PaymentClientAdapter implements PaymentClient {

    public Payment create(Payment payment) {
        log.info("Creating fake payment. amount={}", payment.getAmount());
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setQrCode("https://fake.qr.code");
        payment.setStatus(PENDING);
        payment.setType(PIX);
        return payment;
    }

    public Payment update(Payment payment, PaymentStatus paymentStatus) {
        log.info("Updating fake payment.");
        payment.setStatus(paymentStatus);
        return payment;
    }
}
