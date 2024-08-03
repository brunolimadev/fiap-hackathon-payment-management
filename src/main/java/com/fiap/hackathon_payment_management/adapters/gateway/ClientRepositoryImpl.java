package com.fiap.hackathon_payment_management.adapters.gateway;

import com.fiap.hackathon_payment_management.domain.model.Client;
import com.fiap.hackathon_payment_management.domain.repository.ClientRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepositoryImpl extends MongoRepository<Client, String>, ClientRepository {

}
