package com.fiap.challenge.food.infrastructure.mapper;

import com.fiap.challenge.food.application.request.ConsumerMutation;
import com.fiap.challenge.food.application.request.WebhookRequest;
import com.fiap.challenge.food.application.request.ProductMutation;
import com.fiap.challenge.food.application.response.CartView;
import com.fiap.challenge.food.application.response.ConsumerView;
import com.fiap.challenge.food.application.response.OrderView;
import com.fiap.challenge.food.application.response.PaymentView;
import com.fiap.challenge.food.application.response.ProductView;
import com.fiap.challenge.food.domain.model.cart.Cart;
import com.fiap.challenge.food.domain.model.consumer.Consumer;
import com.fiap.challenge.food.domain.model.order.Order;
import com.fiap.challenge.food.domain.model.payment.Payment;
import com.fiap.challenge.food.domain.model.product.Product;
import com.fiap.challenge.food.domain.model.webhook.Webhook;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ViewMapper {

    ViewMapper INSTANCE = Mappers.getMapper(ViewMapper.class);

    public Cart toCart(CartView cartView);

    public CartView toCartView(Cart cart);

    public ProductView toProductView(Product product);

    public Product toProduct(ProductMutation productMutation);

    public ConsumerView toConsumerView(Consumer consumer);

    public Consumer toConsumer(ConsumerMutation consumerMutation);

    public PaymentView toPaymentView(Payment payment);

    public OrderView toOrderView(Order order);

    public Webhook toWebhook(WebhookRequest webhook);
}
