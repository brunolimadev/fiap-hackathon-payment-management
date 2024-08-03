package com.fiap.hackathon_payment_management.usecase.dto;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import lombok.Getter;

@Getter
public class PaymentDto {
    private String id;
    private double valor;
    private String descricao;
    private String metodoPagamento;
    private String status;
    private String nomeDoCliente;

    public PaymentDto(Payment payment) {
        this.valor = Double.parseDouble(payment.getValue());
        this.descricao = payment.getDescription();
        this.metodoPagamento = payment.getPaymentMethod();
        this.status = payment.getStatus();
        this.nomeDoCliente = payment.getClient().getName();
        this.id = payment.getId();
    }
}
