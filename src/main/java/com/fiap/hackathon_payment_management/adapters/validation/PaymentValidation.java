package com.fiap.hackathon_payment_management.adapters.validation;

import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;

public interface PaymentValidation {
    void validate(PaymentRequestDto requestDto);
}
