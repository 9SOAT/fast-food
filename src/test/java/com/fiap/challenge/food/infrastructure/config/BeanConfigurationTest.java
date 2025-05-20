package com.fiap.challenge.food.infrastructure.config;

import com.fiap.challenge.food.domain.DomainCartService;
import com.fiap.challenge.food.domain.DomainCheckoutService;
import com.fiap.challenge.food.domain.DomainWebhookService;
import com.fiap.challenge.food.domain.ports.inbound.CartService;
import com.fiap.challenge.food.domain.ports.outbound.CartRepository;
import com.fiap.challenge.food.domain.ports.outbound.OrderRepository;
import com.fiap.challenge.food.domain.ports.outbound.PaymentClient;
import com.fiap.challenge.food.domain.ports.outbound.PaymentRepository;
import com.fiap.challenge.food.domain.ports.outbound.ProductRepository;
import com.fiap.challenge.food.domain.ports.outbound.WebhookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BeanConfigurationTest {

    @Test
    @DisplayName("Deve criar DomainCheckoutService com dependências fornecidas")
    void checkoutServiceCreatesDomainCheckoutServiceWithDependencies() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        CartRepository cartRepository = mock(CartRepository.class);
        PaymentClient paymentClient = mock(PaymentClient.class);
        PaymentRepository paymentRepository = mock(PaymentRepository.class);

        BeanConfiguration config = new BeanConfiguration();
        DomainCheckoutService service = config.checkoutService(orderRepository, cartRepository, paymentClient, paymentRepository);

        assertThat(service).isNotNull();
    }

    @Test
    @DisplayName("Deve criar DomainWebhookService com dependências fornecidas")
    void webhookServiceCreatesDomainWebhookServiceWithDependencies() {
        WebhookRepository webhookRepository = mock(WebhookRepository.class);
        OrderRepository orderRepository = mock(OrderRepository.class);
        PaymentRepository paymentRepository = mock(PaymentRepository.class);

        BeanConfiguration config = new BeanConfiguration();
        DomainWebhookService service = config.webhookService(webhookRepository, orderRepository, paymentRepository);

        assertThat(service).isNotNull();
    }

    @Test
    @DisplayName("Deve criar CartService como DomainCartService com dependências fornecidas")
    void cartServiceCreatesDomainCartServiceWithDependencies() {
        CartRepository cartRepository = mock(CartRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);

        BeanConfiguration config = new BeanConfiguration();
        CartService service = config.cartService(cartRepository, productRepository);

        assertThat(service)
            .isNotNull()
            .isInstanceOf(DomainCartService.class);
    }
}
