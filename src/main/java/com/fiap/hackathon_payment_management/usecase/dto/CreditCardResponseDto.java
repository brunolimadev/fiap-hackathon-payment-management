package com.fiap.hackathon_payment_management.usecase.dto;

public record CreditCardResponseDto(
                String id,
                String cpf,
                Double limite,
                String numero,
                String data_validade,
                String cvv) {
}
