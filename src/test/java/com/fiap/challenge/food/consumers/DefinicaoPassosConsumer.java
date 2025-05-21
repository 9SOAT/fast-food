package com.fiap.challenge.food.consumers;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefinicaoPassosConsumer {

    public static final String CONSUMER_URL = "http://a4cd0945c9b014988b80bdcb2859b23c-208191958.us-east-1.elb.amazonaws.com";

    private Response response;
    private String currentCpf;

    @Dado("que o sistema recebe os dados do novo usuário")
    public void receberDadosNovoUsuario() {
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("name", "João dos Santos Alves");
        usuario.put("email", "joao@gmail.com");
        usuario.put("cpf", "44931086063");

        response = given()
            .contentType("application/json")
            .body(usuario)
            .when()
            .post( CONSUMER_URL + "/consumers");

        System.err.println("Usuário cadastrado | Status: " + response.statusCode());
    }

    @Quando("o cadastro for enviado")
    public void envioCadastroUsuario() {
        // Já executado no passo anterior
    }

    @Entao("o usuário deve ser salvo com sucesso")
    public void validarCadastroUsuario() {
        String id = response.jsonPath().getString("cpf");
        System.err.println("Usuário salvo com CPF: " + id);
    }

    @Dado("que existe um usuário com CPF {string}")
    public void usuarioExistenteCpf(String cpf) {
        this.currentCpf = cpf;
        System.err.println("Usuário de CPF existente: " + cpf);
    }

    @Quando("for realizada a busca por esse CPF")
    public void buscarUsuarioPorCpf() {
        response = given()
            .when()
            .get(CONSUMER_URL + "/consumers/" + currentCpf);

        System.err.println("Busca realizada por CPF: " + currentCpf + " | Status: " + response.statusCode());
    }

    @Entao("o sistema deve retornar os dados do usuário correspondente")
    public void validarRetornoUsuarioCpf() {
        String nome = response.jsonPath().getString("name");
        System.err.println("Usuário localizado: " + nome);
    }

    @Dado("que existem múltiplos usuários cadastrados")
    public void usuariosCadastrados() {
        System.err.println("Assumindo existência de múltiplos usuários cadastrados");
    }

    @Quando("for solicitada a página {int} com tamanho {int}")
    public void consultarUsuariosPaginados(int page, int size) {
        response = given()
            .when()
            .get(CONSUMER_URL + "/consumers?page=" + page + "&size=" + size);

        System.err.println("Consulta de usuários paginada | Página: " + page + " | Tamanho: " + size + " | Status: " + response.statusCode());
    }

    @Entao("o sistema deve retornar até {int} usuários")
    public void validarQuantidadeUsuarios(int limite) {
        List<Map<String, Object>> usuarios = response.jsonPath().getList("content");

        if (usuarios.size() > limite) {
            throw new AssertionError("Mais usuários retornados do que o limite permitido: " + usuarios.size());
        }

        System.err.println("Usuários retornados: " + usuarios.size());
    }
}
