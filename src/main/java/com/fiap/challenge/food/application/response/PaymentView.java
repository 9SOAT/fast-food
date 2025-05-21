package com.fiap.challenge.food.application.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentView(
    Long id,
    String qrCode,
    PaymentTypeView type,
    BigDecimal amount,
    String transactionId,
    PaymentStatusView status,
    LocalDateTime approvedAt
) {
}
