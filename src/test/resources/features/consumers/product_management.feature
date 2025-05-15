# language: pt
Funcionalidade: Gerenciamento de Produtos no Catálogo

    @smoke
    Cenário: Cadastro de novo produto
        Dado que o administrador acessa o sistema de catálogo
        Quando cadastrar um novo produto
        Então o produto deve ser salvo com sucesso

    Cenário: Cadastro com dados inválidos
        Dado que o administrador tenta cadastrar um produto sem nome
        Quando submeter os dados incompletos
        Então o sistema deve recusar o cadastro

    Cenário: Atualização de produto
        Dado que o produto com ID 8 está cadastrado
        Quando o administrador alterar o valor para 10
        Então o sistema deve salvar as alterações

    Cenário: Inativação de produto
        Dado que o produto com ID 8 está ativo no catálogo
        Quando o status for alterado para Inativo
        Então o produto não deve ser exibido aos clientes

    Cenário: Filtrar produtos por status
        Dado que existem produtos com status "ACTIVE"
        Quando o cliente filtrar por "ACTIVE"
        Então o sistema deve exibir apenas os produtos com esse status
