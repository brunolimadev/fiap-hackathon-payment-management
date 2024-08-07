package com.fiap.hackathon_payment_management.domain.repository;

import com.fiap.hackathon_payment_management.domain.entity.Payment;

import java.util.List;

public interface PaymentRepository {

    List<Payment> findByClientKey(String clientKey);
    Payment save(Payment payment);

}