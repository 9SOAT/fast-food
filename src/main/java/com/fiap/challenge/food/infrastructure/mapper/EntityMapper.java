package com.fiap.challenge.food.infrastructure.mapper;

import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.model.cart.CartItem;
import com.fiap.challenge.food.domain.model.cart.CartStatus;
import com.fiap.challenge.food.domain.model.consumer.Consumer;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.order.OrderItem;
import com.fiap.challenge.food.domain.model.order.OrderStatus;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.product.ProductCategory;
import com.fiap.challenge.food.domain.model.product.ProductStatus;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import com.fiap.challenge.food.infrastructure.entity.*;
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

    public ConsumerEntity toConsumerEntity(Consumer consumer);

    public Consumer toConsumer(ConsumerEntity consumerEntity);

    public ProductCategoryEntity toCategoryEntity(ProductCategory category);

    public PaymentEntity toPaymentEntity(Payment payment);

    public Payment toPayment(PaymentEntity paymentEntity);

    public OrderItemEntity toOrderItemEntity(OrderItem orderItem);

    public OrderItem toOrderItem(OrderItemEntity orderItemEntity);

    public OrderEntity toOrderEntity(Order order);

    public Order toOrder(OrderEntity orderEntity);

    OrderStatusEntity toOrderStatusEntity(OrderStatus orderStatus);

    ProductStatusEntity toStatusEntity(ProductStatus productStatus);

    WebhookEntity toWebhookEntity(Webhook webhook);

    Webhook toWebhook(WebhookEntity webhook);
}
