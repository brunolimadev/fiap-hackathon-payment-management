package com.fiap.hackathon_payment_management.usecase;

import com.fiap.hackathon_payment_management.domain.repository.PaymentRepository;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;

import java.util.List;
import java.util.stream.Collectors;

public class GetPaymentsByClient {

    private final PaymentRepository paymentRepository;

    public GetPaymentsByClient(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentDto> execute(String clientId) {
        return paymentRepository.findByClientId(clientId).stream()
                .map(PaymentDto::new).collect(Collectors.toList());
    }


}
