# language: pt
Funcionalidade: Gerenciamento de Usuários

    @smoke
    Cenário: Cadastro de novo usuário
        Dado que o sistema recebe os dados do novo usuário
        Quando o cadastro for enviado
        Então o usuário deve ser salvo com sucesso

    Cenário: Consulta de usuário por CPF
        Dado que existe um usuário com CPF "11457801809"
        Quando for realizada a busca por esse CPF
        Então o sistema deve retornar os dados do usuário correspondente

    Cenário: Listagem paginada de usuários
        Dado que existem múltiplos usuários cadastrados
        Quando for solicitada a página 1 com tamanho 5
        Então o sistema deve retornar até 5 usuários
