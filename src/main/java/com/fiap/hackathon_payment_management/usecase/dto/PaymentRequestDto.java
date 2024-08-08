package com.fiap.hackathon_payment_management.usecase.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentRequestDto(
        @NotNull
        String cpf,
        @NotNull
        String numero,
        @NotNull
        String data_validade,
        @NotNull
        String cvv,
        @NotNull
        Double valor
) {
}
