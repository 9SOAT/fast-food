package com.fiap.challenge.food.domain.model.order;

import com.fiap.challenge.food.domain.model.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Order {
    private Long id;
    private Long cartId;
    private Long consumerId;
    private Payment payment;
    private List<OrderItem> items;
    private OrderStatus status;
    private BigDecimal total;

    public void approvePayment() {
        this.status = OrderStatus.READY_FOR_PREPARATION;
        this.payment.approve();
    }

    public boolean isWaitingPaymentStatus() {
        return status.isWaitingPayment()
            && payment.isPendingStatus();
    }

    public long getWaitingMinutes() {
        if (payment.getApprovedAt() == null || !status.isInProgress())
            return 0;

        return ChronoUnit.MINUTES.between(payment.getApprovedAt(), LocalDateTime.now());
    }
}