package com.fiap.hackathon_payment_management.domain.repository;

import com.fiap.hackathon_payment_management.domain.model.Client;

public interface ClientRepository {
    Client findByName(String name);
}
