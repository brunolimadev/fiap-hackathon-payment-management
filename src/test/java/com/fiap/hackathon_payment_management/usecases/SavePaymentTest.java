package com.fiap.hackathon_payment_management.usecases;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.domain.repository.PaymentRepository;
import com.fiap.hackathon_payment_management.usecase.SavePayment;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SavePaymentTest {

  @Mock
  private PaymentRepository paymentRepository;

  private SavePayment savePayment;
  private AutoCloseable openMocks;

  @BeforeEach
  void setup() {

    openMocks = MockitoAnnotations.openMocks(this);
    savePayment = new SavePayment(paymentRepository);

  }

  @AfterEach
  void tearDown() throws Exception {

    openMocks.close();

  }

  @Test
  void shouldRegisterPaymentWithSuccess() {

    //Arrange
    var paymentRequestDto = new PaymentRequestDto("100", "decricao", "metodo", "status", 100.00);
    var paymentEntity = Payment.builder()
                    .id("1")
                    .status("status")
                    .paymentMethod("metodo")
                    .description("descricao")
                    .value(100.00)
            .build();

    when(paymentRepository.save(any(Payment.class))).thenReturn(paymentEntity);

    //Act
    var response = savePayment.execute(paymentRequestDto);

    //Assert
    assertThat(response)
            .isNotNull()
            .isInstanceOf(PaymentDto.class);

    assertThat(response.getPaymentKey()).isEqualTo(paymentEntity.getId());

  }

}