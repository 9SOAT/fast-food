package com.fiap.challenge.food.domain.model.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumer {
    private Long id;
    private String name;
    private String email;
    private String cpf;

    public void setCpf(String cpf) {
        this.cpf = cpf.replaceAll("\\D", "");
    }
}
