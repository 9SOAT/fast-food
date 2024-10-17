package com.fiap.challenge.food.application.response;

public record ConsumerView (
    Long id,
    String name,
    String email,
    String cpf
){}
