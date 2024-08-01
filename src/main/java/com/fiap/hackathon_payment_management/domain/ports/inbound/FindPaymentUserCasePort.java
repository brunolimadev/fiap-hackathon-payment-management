package com.fiap.hackathon_payment_management.domain.ports.inbound;

import com.fiap.hackathon_payment_management.domain.entities.Payment;
import com.fiap.hackathon_payment_management.domain.exceptions.PaymentNotFundException;

import java.util.List;

public interface FindPaymentUserCasePort {
    List<Payment> execute(String key) throws PaymentNotFundException;
}
