package com.fiap.hackathon_payment_management.usecase.dto;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import lombok.Getter;

@Getter
public class PaymentDto {
    private String valor;
    private String descricao;
    private String metodoPagamento;
    private String status;

    public PaymentDto(Payment payment) {
        this.valor = payment.getValue();
        this.descricao = payment.getDescription();
        this.metodoPagamento = payment.getPaymentMethod();
        this.status = payment.getStatus();
    }
}
