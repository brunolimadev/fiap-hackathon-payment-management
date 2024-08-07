package com.fiap.hackathon_payment_management.usecase;

import com.fiap.hackathon_payment_management.domain.repository.PaymentRepository;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import com.fiap.hackathon_payment_management.utils.ConvertDtoToEntity;
import com.fiap.hackathon_payment_management.utils.ConvertEntityToDto;

public class SavePayment {

    private final PaymentRepository paymentRepository;

    public SavePayment(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentDto execute(PaymentRequestDto paymentDto) {

        var payment = ConvertDtoToEntity.convert(paymentDto);

        return ConvertEntityToDto.convert(paymentRepository.save(payment));

    }

}