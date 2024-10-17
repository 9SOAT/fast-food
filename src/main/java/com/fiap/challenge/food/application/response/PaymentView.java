package com.fiap.challenge.food.application.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentView(
    String qrCode,
    PaymentTypeView type,
    BigDecimal amount,
    PaymentStatusView status,
    LocalDateTime approvedAt
) {
}
