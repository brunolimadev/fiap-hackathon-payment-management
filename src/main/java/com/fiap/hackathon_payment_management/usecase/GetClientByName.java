package com.fiap.hackathon_payment_management.usecase;

import com.fiap.hackathon_payment_management.domain.repository.ClientRepository;
import com.fiap.hackathon_payment_management.usecase.dto.ClientDto;

public class GetClientByName {

    private final ClientRepository clientRepository;

    public GetClientByName(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDto execute(String name) {
        return new ClientDto(clientRepository.findByName(name));
    }
}
