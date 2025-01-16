package com.fiap.challenge.food.infrastructure.integration;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MercadoPagoClient extends PaymentClient {

    public MercadoPagoClient(
        @Value("${mercado-pago.accessToken}") String accessToken
    ) {
        MercadoPagoConfig.setAccessToken(accessToken);
    }
}
