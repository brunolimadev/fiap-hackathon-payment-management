package com.fiap.hackathon_payment_management.usecase;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.domain.repository.PaymentRepository;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;

public class SavePayment {

    private final PaymentRepository paymentRepository;

    public SavePayment(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentDto execute(PaymentRequestDto paymentDto) {
        var payment = convertPaymentDtoToPaymentEntity(paymentDto);
        return new PaymentDto(paymentRepository.save(payment));
    }

    private Payment convertPaymentDtoToPaymentEntity(PaymentRequestDto paymentDto) {
        return Payment.builder()
                .clientKey(paymentDto.cpf())
                .value(paymentDto.valor())
                .description("Descrição do Pagamento")
                .paymentMethod("cartao_credito")
                .status("aprovado")
                .build();
    }
}
