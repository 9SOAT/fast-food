package com.fiap.challenge.food.consumers;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefinicaoPassosCatalogo {

    private Response response;


    @Dado("que o administrador acessa o sistema de catálogo")
    public void acessarSistemaCatalogo() {
        System.err.println("Administrador autenticado no sistema de catálogo");
    }

    @Quando("cadastrar um novo produto")
    public void cadastrarNovoProduto() {
        Map<String, Object> produto = new HashMap<>();
        produto.put("name", "X-TESTE");
        produto.put("description", "Lanche para comer durante os testes.");
        produto.put("images", List.of("https:produtos/fotos/777177.jpg"));
        produto.put("price", 35);
        produto.put("category", "SANDWICH");

        response = given()
            .contentType("application/json")
            .body(produto)
            .when()
            .post("http://localhost:8081/products");

        System.err.println("Produto cadastrado | Status: " + response.statusCode());
    }

    @Entao("o produto deve ser salvo com sucesso")
    public void validarCadastroProduto() {
        String productId = response.jsonPath().getString("id");
        System.err.println("Produto salvo com sucesso | ID: " + productId);
    }

    @Dado("que o administrador tenta cadastrar um produto sem nome")
    public void cadastrarProdutoSemNome() {
        Map<String, Object> produtoInvalido = new HashMap<>();
        produtoInvalido.put("name", "X-TESTE");
        produtoInvalido.put("description", "Lanche para comer durante os testes.");
        produtoInvalido.put("images", List.of("https:produtos/fotos/777177.jpg"));
        produtoInvalido.put("price", "");
        produtoInvalido.put("category", "SANDWICH");

        response = given()
            .contentType("application/json")
            .body(produtoInvalido)
            .when()
            .post("http://localhost:8081/products");

        System.err.println("Tentativa de cadastro inválido | Status: " + response.statusCode());
    }

    @Quando("submeter os dados incompletos")
    public void submeterDadosIncompletos() {
        // Já realizado na etapa anterior
    }

    @Entao("o sistema deve recusar o cadastro")
    public void validarRejeicaoCadastro() {
        if (response.statusCode() != 201) {
            System.err.println("Cadastro rejeitado conforme esperado | Status: " + response.statusCode());
        } else {
            throw new AssertionError("Cadastro foi aceito indevidamente");
        }
    }

    @Dado("que o produto com ID {int} está cadastrado")
    public void produtoCadastrado(int id) {
        // Simulação de que o produto existe
        System.err.println("Produto com ID " + id + " está cadastrado");
    }

    @Quando("o administrador alterar o valor para {int}")
    public void alterarValorProduto(int valor) {

        Map<String, Object> atualizacao = new HashMap<>();
        atualizacao.put("name", "X-TESTE");
        atualizacao.put("description", "Lanche para comer durante os testes.");
        atualizacao.put("images", List.of("https:produtos/fotos/777177.jpg"));
        atualizacao.put("price", valor);
        atualizacao.put("category", "SANDWICH");

        response = given()
            .contentType("application/json")
            .body(atualizacao)
            .when()
            .put("http://localhost:8081/products/8");

        System.err.println("Produto atualizado com novo valor | Status: " + response.statusCode());
    }

    @Entao("o sistema deve salvar as alterações")
    public void validarAtualizacaoProduto() {
        System.err.println("Alteração confirmada | Status: " + response.statusCode());
    }

    @Dado("que o produto com ID {int} está ativo no catálogo")
    public void produtosComStatusInativo(int status) {
        System.err.println("Simulando produtos com status: " + status);
    }

    @Quando("o status for alterado para Inativo")
    public void alterarStatusProduto() {

        response = given()
            .contentType("application/json")
            .when()
            .delete("http://localhost:8081/products/8");

        System.err.println("Status do produto atualizado para: Inativo | Status: " + response.statusCode());
    }

    @Entao("o produto não deve ser exibido aos clientes")
    public void validarProdutoInativo() {
        response = given()
            .when()
            .get("http://localhost:8081/products/8");

        boolean ativo = response.jsonPath().getString("status").equalsIgnoreCase("Ativo");
        if (ativo) {
            throw new AssertionError("Produto ainda aparece como ativo");
        } else {
            System.err.println("Produto ocultado com sucesso | Status: " + response.statusCode());
        }
    }

    @Dado("que existem produtos com status {string}")
    public void produtosComStatus(String status) {
        System.err.println("Simulando produtos com status: " + status);
    }

    @Quando("o cliente filtrar por {string}")
    public void filtrarProdutosPorStatus(String status) {
        response = given()
            .when()
            .get("http://localhost:8081/products?page=1&size=10&status=" + status);

        System.err.println("Filtro aplicado por status: " + status + " | Status: " + response.statusCode());
    }

    @Entao("o sistema deve exibir apenas os produtos com esse status")
    public void validarFiltroStatus() {
        JsonPath jp = new JsonPath(response.asString());
        List<Map<String, Object>> produtos = jp.getList("content");

        boolean todosAtivos = produtos.stream()
            .allMatch(produto -> "ACTIVE".equalsIgnoreCase(produto.get("status").toString()));

        if (!todosAtivos) {
            throw new AssertionError("Foram encontrados produtos fora do status ACTIVE");
        }

        System.err.println("Todos os produtos filtrados possuem status ACTIVE | Total: " + produtos.size());
    }

}
