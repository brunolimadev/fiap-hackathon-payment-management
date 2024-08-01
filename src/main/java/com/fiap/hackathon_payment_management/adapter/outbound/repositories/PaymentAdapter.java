package com.fiap.hackathon_payment_management.adapter.outbound.repositories;

import com.fiap.hackathon_payment_management.adapter.outbound.repositories.interfaces.PaymentRepository;
import com.fiap.hackathon_payment_management.adapter.outbound.repositories.models.PaymentModel;
import com.fiap.hackathon_payment_management.domain.entities.Payment;
import com.fiap.hackathon_payment_management.domain.entities.ports.outbound.PaymentAdapterPort;
import com.fiap.hackathon_payment_management.domain.exceptions.PaymentNotFundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PaymentAdapter implements PaymentAdapterPort {


    private final PaymentRepository paymentRepository;

    public PaymentAdapter( PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findPaymentByClientKey(String key) throws PaymentNotFundException {
        return paymentRepository.findPaymentsByClientKey(key);
    }
}
