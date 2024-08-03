package com.fiap.hackathon_payment_management.usecase.dto;

public record PaymentRequestDto(
        String valor,
        String descricao,
        String metodoPagamento,
        String status,
        String nomeDoCliente
) {
}
