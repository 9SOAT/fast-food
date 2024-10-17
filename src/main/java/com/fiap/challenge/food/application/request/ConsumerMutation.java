package com.fiap.challenge.food.application.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record ConsumerMutation(
    @NotEmpty String name,
    @NotEmpty @Email String email,
    @NotNull @CPF String cpf
) {
}
