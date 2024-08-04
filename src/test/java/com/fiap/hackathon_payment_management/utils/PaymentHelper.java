package com.fiap.hackathon_payment_management.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;

public abstract class PaymentHelper {

    public static PaymentDto gerarPaymentDto() {
        return new PaymentDto(Payment.builder()
                .paymentMethod("cartao-de-credito")
                .description("Nome de teste do pagamento")
                .status("aprovado")
                .clientKey("02")
                .build());
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}
