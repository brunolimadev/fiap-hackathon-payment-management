package com.fiap.hackathon_payment_management.usecase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PaymentDto {

    @JsonProperty("chave_pagamento")
    private String paymentKey;
    @JsonProperty("valor")
    private String value;
    @JsonProperty("descricao")
    private String description;
    @JsonProperty("metodo_pagamento")
    private String paymentMethod;
    @JsonProperty("status")
    private String status;

}