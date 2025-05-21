package com.fiap.challenge.food.consumers;

import com.fiap.challenge.food.application.request.PaymentWebhookStatus;
import com.fiap.challenge.food.application.request.WebhookAction;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.E;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class DefinicaoPassosPedido {

    public static final String CONSUMER_URL = "http://a4cd0945c9b014988b80bdcb2859b23c-208191958.us-east-1.elb.amazonaws.com";
    public static final String FAST_FOOD_URL = "http://a0ef7dae8bc5b439cb42c70027235315-1121953028.us-east-1.elb.amazonaws.com";
    public static final String ORDERS_URL = "http://ae74f709671da44f9ae9c7279548d4e1-1790224368.us-east-1.elb.amazonaws.com";

    private static String currentCpf;
    private static String consumerId;
    private static Long orderId;
    private static Response response;
    private static Map<String, Object> item = new HashMap<>();
    private static Integer cartId;

    @Dado("que o usuário informa o CPF {string}")
    public void informarCpfUsuario(String cpf) {
        currentCpf = cpf;
        System.err.println("Identificando CPF: " + currentCpf);
    }

    @Quando("a requisição for enviada com dados válidos")
    public void enviarRequisicaoComDadosValidos() {
        response = given()
            .when()
            .get(CONSUMER_URL + "/consumers/" + currentCpf);
        System.err.println("Consulta enviada para CPF " + currentCpf + " | status_code: " + response.statusCode());
    }

    @Entao("o usuário deve ser identificado")
    public void validarIdentificacaoUsuario() {
        consumerId = response.jsonPath().getString("cpf");
        System.err.println("Usuário identificado: ID " + consumerId);
    }

    @E("o carrinho deve ser criado")
    public void criarCarrinhoUsuario() {
        Map<String, Object> cartRequest = new HashMap<>();
        cartRequest.put("consumerId", consumerId);

        response = given()
            .contentType("application/json")
            .body(cartRequest)
            .when()
            .post(FAST_FOOD_URL + "/carts");
        System.err.println("Carrinho criado com sucesso | status_code: " + response.statusCode());

        cartId = response.jsonPath().getInt("id");
        System.err.println("ID do carrinho gerado: " + cartId);
    }

    @Dado("que o usuário adicionou {int} unidades do produto {string}")
    public void adicionarItemAoCarrinho(int quantidade, String produtoId) {
        item.put("productId", produtoId);
        item.put("quantity", quantidade);
        System.err.println("Itens incluídos: " + item);
    }

    @Quando("confirmar os itens")
    public void confirmarItensCarrinho() {
        response = given()
            .contentType("application/json")
            .body(item)
            .when()
            .post(FAST_FOOD_URL + "/carts/" + cartId + "/items")
            .then()
            .extract().response();
        System.err.println("Itens confirmados no carrinho | status_code: " + response.statusCode());
    }

    @Entao("o carrinho deve ser atualizado")
    public void validarCarrinhoAtualizado() {
        String createdId = response.jsonPath().getString("id");
        System.err.println("Carrinho finalizado: ID " + createdId);
    }

    @Dado("que o usuário confirmou os itens no carrinho")
    public void recuperarCarrinhoParaCheckout() {
        response = given()
            .when()
            .get(FAST_FOOD_URL + "/carts/" + cartId);
        System.err.println("Recuperado carrinho: " + cartId + " - status_code: " + response.statusCode());
    }

    @Quando("realizar o checkout no carrinho")
    public void executarCheckoutCarrinho() {
        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("cartId", cartId);

        response = given()
            .contentType("application/json")
            .body(orderRequest)
            .when()
            .post(FAST_FOOD_URL + "/checkout");
        System.err.println("Pedido gerado a partir do carrinho - status_code: " + response.statusCode());
    }

    @Entao("o pedido deve ser criado")
    public void validarPedidoCriado() {
        orderId = response.jsonPath().getLong("id");
        System.err.println("Pedido criado com ID: " + orderId);
    }

    @E("o pagamento concluído")
    public void validarPagamentoConcluido() {
        Map<String, Object> webhookRequest = new HashMap<>();
        webhookRequest.put("transactionId", response.jsonPath().getString("payment.transactionId"));
        webhookRequest.put("action", WebhookAction.UPDATED);
        webhookRequest.put("status", PaymentWebhookStatus.APPROVED);
        webhookRequest.put("dateCreated", ZonedDateTime.now());

        response = given()
            .contentType("application/json")
            .body(webhookRequest)
            .when()
            .post(FAST_FOOD_URL + "/webhook");
        System.err.println("Pagamento realizado - status_code: " + response.statusCode());
    }

    @Dado("que o preparo do pedido foi iniciado")
    public void recuperarPedidoParaPreparacao() {
        response = given()
            .when()
            .get(ORDERS_URL + "/orders?page=1&size=5&status=READY_FOR_PREPARATION");

        JsonPath jp = new JsonPath(response.asString());
        orderId = jp.getLong("content[0].id");
        String status = jp.getString("content[0].status");

        System.err.println("Pedido " + orderId + " com status: " + status + " - status_code: " + response.statusCode());
    }

    @Quando("o status for alterado para PREPARANDO")
    public void atualizarStatusParaPreparando() {
        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("status", "IN_PREPARATION");

        response = given()
            .contentType("application/json")
            .body(orderRequest)
            .when()
            .patch(ORDERS_URL + "/orders/" + orderId + "/status");
        System.err.println("Status atualizado para PREPARANDO - status_code: " + response.statusCode());
    }

    @Entao("o pedido deve refletir o status PREPARANDO")
    public void validarStatusPreparando() {
        response = given()
            .when()
            .get(ORDERS_URL + "/orders?page=1&size=5&status=IN_PREPARATION");

        JsonPath jp = new JsonPath(response.asString());
        orderId = jp.getLong("content[0].id");
        String status = jp.getString("content[0].status");

        System.err.println("Pedido " + orderId + " com status: " + status + " - status_code: " + response.statusCode());
    }

    @Dado("que o preparo foi finalizado")
    public void recuperarPedidoProntoParaRetirada() {
        response = given()
            .when()
            .get(ORDERS_URL + "/orders?page=1&size=5&status=IN_PREPARATION");

        JsonPath jp = new JsonPath(response.asString());
        orderId = jp.getLong("content[0].id");
        String status = jp.getString("content[0].status");

        System.err.println("Pedido " + orderId + " com status: " + status + " - status_code: " + response.statusCode());
    }

    @Quando("o status for alterado para PRONTO PARA RETIRAR")
    public void atualizarStatusParaProntoParaRetirar() {
        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("status", "READY_FOR_PICKUP");

        response = given()
            .contentType("application/json")
            .body(orderRequest)
            .when()
            .patch(ORDERS_URL + "/orders/" + orderId + "/status");
        System.err.println("Status atualizado para PRONTO PARA RETIRAR - status_code: " + response.statusCode());
    }

    @Entao("o pedido deve refletir o status PRONTO PARA RETIRAR")
    public void validarStatusProntoParaRetirar() {
        response = given()
            .when()
            .get(ORDERS_URL + "/orders?page=1&size=5&status=READY_FOR_PICKUP");

        JsonPath jp = new JsonPath(response.asString());
        orderId = jp.getLong("content[0].id");
        String status = jp.getString("content[0].status");

        System.err.println("Pedido " + orderId + " com status: " + status + " - status_code: " + response.statusCode());
    }

    @Dado("que o pedido foi finalizado")
    public void recuperarPedidoFinalizado() {
        response = given()
            .when()
            .get(ORDERS_URL + "/orders?page=1&size=5&status=READY_FOR_PICKUP");

        JsonPath jp = new JsonPath(response.asString());
        orderId = jp.getLong("content[0].id");
        String status = jp.getString("content[0].status");

        System.err.println("Pedido " + orderId + " com status: " + status + " - status_code: " + response.statusCode());
    }

    @Quando("o status for alterado para FINALIZADO")
    public void atualizarStatusParaFinalizado() {
        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("status", "FINISHED");

        response = given()
            .contentType("application/json")
            .body(orderRequest)
            .when()
            .patch(ORDERS_URL + "/orders/" + orderId + "/status");
        System.err.println("Status atualizado para FINALIZADO - status_code: " + response.statusCode());
    }

    @Entao("o pedido deve refletir o status FINALIZADO")
    public void validarStatusFinalizado() {
        response = given()
            .when()
            .get(ORDERS_URL + "/orders?page=1&size=5&status=FINISHED");

        JsonPath jp = new JsonPath(response.asString());
        orderId = jp.getLong("content[0].id");
        String status = jp.getString("content[0].status");

        System.err.println("Pedido " + orderId + " com status: " + status + " - status_code: " + response.statusCode());
    }
}
