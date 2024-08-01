package com.fiap.hackathon_payment_management.domain.entities.ports.outbound;

import com.fiap.hackathon_payment_management.domain.entities.Payment;
import com.fiap.hackathon_payment_management.domain.exceptions.PaymentNotFundException;

import java.util.List;
import java.util.Optional;

public interface PaymentAdapterPort {
    List<Payment> findPaymentByClientKey(String key) throws PaymentNotFundException;
}
