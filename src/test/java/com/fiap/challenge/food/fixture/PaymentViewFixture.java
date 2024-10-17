package com.fiap.challenge.food.fixture;

import com.fiap.challenge.food.application.response.PaymentStatusView;
import com.fiap.challenge.food.application.response.PaymentTypeView;
import com.fiap.challenge.food.application.response.PaymentView;

import java.math.BigDecimal;

public class PaymentViewFixture {
    public static PaymentView validPaymentView() {
        return new PaymentView(
            "123456",
            PaymentTypeView.PIX,
            BigDecimal.valueOf(100),
            PaymentStatusView.PENDING,
            null
        );
    }
}
