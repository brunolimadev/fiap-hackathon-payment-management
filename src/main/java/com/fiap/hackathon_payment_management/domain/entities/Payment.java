package com.fiap.hackathon_payment_management.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    private String valor;
    private String descricao;
    private String metodo_Pagamento;
    private String status;
}
