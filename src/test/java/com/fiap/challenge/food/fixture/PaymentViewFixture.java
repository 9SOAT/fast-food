package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.application.response.PaymentStatusView;
import com.fiap.challenge.food.application.response.PaymentTypeView;
import com.fiap.challenge.food.application.response.PaymentView;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentViewFixture {
    public static PaymentView validPaymentView() {
        return new PaymentView(
            1L,
            "123456",
            PaymentTypeView.PIX,
            BigDecimal.valueOf(100),
            UUID.randomUUID().toString(),
            PaymentStatusView.PENDING,
            null
        );
    }
}
