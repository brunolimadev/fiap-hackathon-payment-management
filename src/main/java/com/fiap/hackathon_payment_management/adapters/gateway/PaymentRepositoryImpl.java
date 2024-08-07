package com.fiap.hackathon_payment_management.adapters.gateway;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.domain.repository.PaymentRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepositoryImpl extends MongoRepository<Payment, String>, PaymentRepository {

    @Override
    default List<Payment> findByClientKey(String clientKey) {
        return findAllByClientKey(clientKey);
    }

    List<Payment> findAllByClientKey(String clientKey);
}
