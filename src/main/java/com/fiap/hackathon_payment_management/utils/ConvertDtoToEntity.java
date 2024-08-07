package com.fiap.hackathon_payment_management.utils;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConvertDtoToEntity {

  public static Payment convert(PaymentRequestDto paymentDto) {

    return Payment.builder()
            .clientKey(paymentDto.cpf())
            .value(paymentDto.valor())
            .description("Descrição do Pagamento")
            .paymentMethod("cartao_credito")
            .status("aprovado")
            .build();
  }

}