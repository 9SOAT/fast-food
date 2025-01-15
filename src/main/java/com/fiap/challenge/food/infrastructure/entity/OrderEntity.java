package com.fiap.challenge.food.infrastructure.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders", indexes = {
    @Index(name = "idx_orders_consumer_id", columnList = "consumer_id"),
    @Index(name = "idx_orders_status", columnList = "status"),
    @Index(name = "uk_orders_cart_id", columnList = "cart_id", unique = true)
})
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long consumerId;

    @NotNull
    private Long cartId;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private PaymentEntity payment;

    @NotNull
    @Enumerated(STRING)
    private OrderStatusEntity status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItemEntity> items;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(scale = 2)
    private BigDecimal total;

    @Column
    @NotNull
    private Instant createdAt;

    public void setItems(List<OrderItemEntity> items) {
        items.forEach(item -> item.setOrder(this));
        this.items = items;
    }

    public void setPayment(PaymentEntity payment) {
        payment.setOrder(this);
        this.payment = payment;
    }
}
