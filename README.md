# 🍔 Fast Food  

O Fast Food é um sistema que permite que o cliente faça pedidos de forma autônoma, sem a necessidade de um atendente. O cliente pode visualizar o cardápio, adicionar itens ao carrinho, visualizar o carrinho, finalizar o pedido e realizar o pagamento. Após o pagamento, o cliente recebe um código de retirada que pode ser utilizado para acompanhar o status do pedido e para retirada o pedido no balcão.

## 🍟 Tecnologias e Técnicas utilizadas

Neste projeto são utilizadas as seguintes tecnologias:

- **Spring Boot**: Framework Java para criação de aplicações web


- **Java 21**: Linguagem de programação


- **Docker**: Plataforma para desenvolvimento, envio e execução de aplicações em containers


- **Docker Compose**: Ferramenta para definir e executar aplicativos Docker multi-container


- **Postgres**: Banco de dados relacional


- **Liquibase**: Ferramenta para controle de versão de banco de dados


- **Swagger**: Framework para documentação de APIs REST


- **JUnit**: Framework para criação de testes unitários


- **Mockito**: Framework para criação de mocks em testes


- **Hexagonal Architecture**: Arquitetura de software que visa separar a lógica de negócio da lógica de infraestrutura

## 🛠️ Execução Local

Pré-requisitos: 
- Ter o Docker e Docker Compose instalados

### Docker Compose
```shell
docker compose -p fast-food up -d
```

Para acessar a aplicação, execute o comando:

```shell
  kubectl port-forward svc/fast-food 8080:8080
```

#### Helm
- Ter o Helm instalado.

```shell
  helm install fast-food ./helm/fast-food
```

## 📖 Documentação

Como parte da documentação, foram elaborados alguns artefatos para auxiliar no entendimento da equipe, incluindo:
- Diagramas do C4 Model nas camadas Context, Container e Component, para oferecer uma visão abrangente e estruturada da arquitetura do sistema.
  - [Context](/docs/c4-model/systemcontext.png)
  - [Container](/docs/c4-model/container.png)
  - [Component](/docs/c4-model/component.png)
- Um diagrama de Kubernetes (K8s), acompanhado de uma análise do problema enfrentado: o restaurante está lidando com questões de performance em seu totem.
  - [Diagrama](/docs/k8s/sk8.png)
- Requests (API)
  - Swagger: http://localhost:8080/swagger-ui/index.html
  - [Postman para importar] - FALTA

## 🍨 Arquitetura Hexagonal

A arquitetura hexagonal, também conhecida como Arquitetura de Portas e Adaptadores, é um estilo de arquitetura de software que visa separar a lógica de negócio da lógica de infraestrutura. Ela promove a criação de sistemas que são mais fáceis de manter, testar e evoluir ao longo do tempo.  

#### Principais Conceitos

1. Domínio: Contém a lógica de negócio central da aplicação. É independente de qualquer tecnologia ou framework específico.
2. Portos: Interfaces que definem como a aplicação se comunica com o mundo externo (entrada) e como o mundo externo se comunica com a aplicação (saída).
3. Adaptadores: Implementações concretas das portas. Eles adaptam a comunicação entre o domínio e as tecnologias externas, como bancos de dados, APIs, interfaces de usuário, etc.

#### Estrutura

A arquitetura hexagonal é geralmente representada como um hexágono, onde:

* O núcleo (centro) do hexágono é o domínio.
* As bordas do hexágono são as portas.
* Fora do hexágono estão os adaptadores.

#### Benefícios

* Isolamento da Lógica de Negócio: A lógica de negócio é isolada de detalhes de implementação, facilitando mudanças e testes.
* Facilidade de Testes: Como a lógica de negócio é independente de infraestrutura, testes unitários podem ser realizados sem a necessidade de dependências externas.
* Flexibilidade: Facilita a troca de tecnologias e frameworks sem impactar a lógica de negócio.



#### 📂 Estrutura de pacotes do projeto

```
.
├── application
│   ├── controller
│   ├── request
│   └── response
├── domain
│   ├── model
│   └── ports
│       ├── inbound
│       └── outbound
└── infrastructure
    ├── config
    ├── entity
    ├── integration
    ├── mapper
    └── repository

```


Hexagonal:<img src="./docs/Hexagonal.png" alt="Hexagonal"></img>


Exemplo de Implementação:<img src="./docs/Hexagonal-impl.png" alt="Hexagonal Impl"></img>

Essa estrutura de pacotes e a separação clara de responsabilidades ajudam a manter o código organizado e modular, facilitando a manutenção e evolução do sistema.





