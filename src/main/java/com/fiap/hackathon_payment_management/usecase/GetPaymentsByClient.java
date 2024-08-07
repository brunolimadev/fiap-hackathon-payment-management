package com.fiap.hackathon_payment_management.usecase;

import com.fiap.hackathon_payment_management.domain.repository.PaymentRepository;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.utils.ConvertEntityToDto;

import java.util.List;
import java.util.stream.Collectors;

public class GetPaymentsByClient {

    private final PaymentRepository paymentRepository;

    public GetPaymentsByClient(PaymentRepository paymentRepository) {

        this.paymentRepository = paymentRepository;

    }

    public List<PaymentDto> execute(String clientKey) {

        return paymentRepository.findByClientKey(clientKey).stream()
                .map(ConvertEntityToDto::convertAllValues)
                .collect(Collectors.toList());

    }

}