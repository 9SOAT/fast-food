# language: pt
Funcionalidade: Fluxo de Pedido

  Cenário: Identificação do usuário
      Dado que o usuário informa o CPF "42885136855"
      Quando a requisição for enviada com dados válidos
      Então o usuário deve ser identificado
      E o carrinho deve ser criado

    Cenário: Criação de pedido
        Dado que o usuário adicionou 2 unidades do produto "682d098a1ceeae73ebed9b03"
        Quando confirmar os itens
        Então o carrinho deve ser atualizado

    Cenário: Pagamento do pedido
        Dado que o usuário confirmou os itens no carrinho
        Quando realizar o checkout no carrinho
        Então o pedido deve ser criado
        E o pagamento concluído

    Cenário: Atualizar status para PREPARANDO
        Dado que o preparo do pedido foi iniciado
        Quando o status for alterado para PREPARANDO
        Então o pedido deve refletir o status PREPARANDO

    Cenário: Atualizar status para PRONTO PARA RETIRAR
        Dado que o preparo foi finalizado
        Quando o status for alterado para PRONTO PARA RETIRAR
        Então o pedido deve refletir o status PRONTO PARA RETIRAR

    Cenário: Atualizar status para FINALIZADO
        Dado que o pedido foi finalizado
        Quando o status for alterado para FINALIZADO
        Então o pedido deve refletir o status FINALIZADO





