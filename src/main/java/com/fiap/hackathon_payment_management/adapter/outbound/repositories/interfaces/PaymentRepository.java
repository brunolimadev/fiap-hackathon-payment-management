package com.fiap.hackathon_payment_management.adapter.outbound.repositories.interfaces;

import com.fiap.hackathon_payment_management.adapter.outbound.repositories.models.PaymentModel;
import com.fiap.hackathon_payment_management.domain.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {
    List<Payment> findPaymentsByClientKey(String key);
}
