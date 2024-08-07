package com.fiap.hackathon_payment_management.utils;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConvertEntityToDto {

  public static PaymentDto convert(Payment payment) {

    return PaymentDto.builder()
            .paymentKey(payment.getId())
            .build();
  }

  public static PaymentDto convertAllValues(Payment payment) {

    return PaymentDto.builder()
            .value(payment.getValue())
            .description(payment.getDescription())
            .paymentMethod(payment.getPaymentMethod())
            .status(payment.getStatus())
            .build();
  }

}