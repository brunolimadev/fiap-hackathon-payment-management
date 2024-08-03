package com.fiap.hackathon_payment_management.usecase;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.domain.repository.ClientRepository;
import com.fiap.hackathon_payment_management.domain.repository.PaymentRepository;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;

public class SavePayment {

    private final PaymentRepository paymentRepository;

    private final ClientRepository clientRepository;

    public SavePayment(PaymentRepository paymentRepository, ClientRepository clientRepository) {
        this.paymentRepository = paymentRepository;
        this.clientRepository = clientRepository;
    }

    public PaymentDto execute(PaymentRequestDto paymentDto) {
        var payment = convertPaymentDtoToPaymentEntity(paymentDto);
        return new PaymentDto(paymentRepository.save(payment));
    }

    private Payment convertPaymentDtoToPaymentEntity(PaymentRequestDto paymentDto) {
        return Payment.builder()
                .client(clientRepository.findByName(paymentDto.nomeDoCliente()))
                .value(String.valueOf(paymentDto.valor()))
                .description(paymentDto.descricao())
                .paymentMethod(paymentDto.metodoPagamento())
                .status(paymentDto.status())
                .build();
    }
}
