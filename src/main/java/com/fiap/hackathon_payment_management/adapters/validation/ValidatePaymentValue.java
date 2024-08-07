package com.fiap.hackathon_payment_management.adapters.validation;

import com.fiap.hackathon_payment_management.adapters.validation.exception.ValidationException;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ValidatePaymentValue implements PaymentValidation {

    @Override
    public void validate(PaymentRequestDto requestDto) {

        if (isNegative(requestDto.valor())) {
            throw new ValidationException("O valor da compra não pode ser negativa.");
        }

    }

    public static boolean isNegative(String value) {
        try {
            double number = Double.parseDouble(value);
            return number < 0;
        } catch (NumberFormatException e) {
            throw new ValidationException("Não é um valor válido.");
        }
    }
}
