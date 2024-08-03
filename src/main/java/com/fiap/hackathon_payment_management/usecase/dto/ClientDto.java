package com.fiap.hackathon_payment_management.usecase.dto;

import com.fiap.hackathon_payment_management.domain.entity.Client;
import lombok.Getter;

@Getter
public class ClientDto {
    private String id;
    private String nome;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.nome = client.getName();
    }
}
