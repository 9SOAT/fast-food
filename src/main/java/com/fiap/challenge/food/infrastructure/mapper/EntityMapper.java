package com.fiap.challenge.food.infrastructure.mapper;

import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.model.cart.CartItem;
import com.fiap.challenge.food.domain.model.cart.CartStatus;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.order.OrderItem;
import com.fiap.challenge.food.domain.model.order.OrderStatus;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.infrastructure.entity.CartEntity;
import com.fiap.challenge.food.infrastructure.entity.CartItemEntity;
import com.fiap.challenge.food.infrastructure.entity.CartStatusEntity;
import com.fiap.challenge.food.infrastructure.entity.OrderEntity;
import com.fiap.challenge.food.infrastructure.entity.OrderItemEntity;
import com.fiap.challenge.food.infrastructure.entity.OrderStatusEntity;
import com.fiap.challenge.food.infrastructure.entity.PaymentEntity;
import com.fiap.challenge.food.infrastructure.entity.ProductEntity;
import com.fiap.challenge.food.infrastructure.entity.WebhookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    public Cart toCart(CartEntity cartEntity);

    public CartEntity toCartEntity(Cart cart);

    public CartItem toCartItem(CartItemEntity cartItemEntity);

    public CartItemEntity toCartItemEntity(CartItem cartItem);

    public CartStatusEntity toCartStatusEntity(CartStatus status);

    public Product toProduct(ProductEntity productEntity);

    public ProductEntity toProductEntity(Product product);

    public PaymentEntity toPaymentEntity(Payment payment);

    public Payment toPayment(PaymentEntity paymentEntity);

    public OrderItemEntity toOrderItemEntity(OrderItem orderItem);

    public OrderItem toOrderItem(OrderItemEntity orderItemEntity);

    public OrderEntity toOrderEntity(Order order);

    public Order toOrder(OrderEntity orderEntity);

    OrderStatusEntity toOrderStatusEntity(OrderStatus orderStatus);

    WebhookEntity toWebhookEntity(Webhook webhook);

    Webhook toWebhook(WebhookEntity webhook);
}
