package com.fiap.hackathon_payment_management.domain.usecases;

import com.fiap.hackathon_payment_management.domain.entities.Payment;
import com.fiap.hackathon_payment_management.domain.entities.ports.outbound.PaymentAdapterPort;
import com.fiap.hackathon_payment_management.domain.exceptions.PaymentNotFundException;
import com.fiap.hackathon_payment_management.domain.ports.inbound.FindPaymentUserCasePort;

import java.util.List;

public class FindPaymentUserCase implements FindPaymentUserCasePort {

    private final PaymentAdapterPort paymentAdapterPort;

    public FindPaymentUserCase(PaymentAdapterPort paymentAdapterPort) {
        this.paymentAdapterPort = paymentAdapterPort;
    }

    @Override
    public List<Payment> execute(String key) throws PaymentNotFundException {
        return List.of();
    }
}
