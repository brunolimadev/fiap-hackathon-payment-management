package com.fiap.hackathon_payment_management.usecases;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.domain.repository.PaymentRepository;
import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetPaymentsByClientTest {

  @Mock
  private PaymentRepository paymentRepository;

  private GetPaymentsByClient getPaymentsByClient;
  private AutoCloseable openMocks;

  @BeforeEach
  void setup() {

    openMocks = MockitoAnnotations.openMocks(this);
    getPaymentsByClient = new GetPaymentsByClient(paymentRepository);

  }

  @AfterEach
  void tearDown() throws Exception {

    openMocks.close();

  }

  @Test
  void shouldGetPaymentsByClientWithSuccess() {

    //Arrange
    var paymentRequestDto = new PaymentRequestDto("100", "decricao", "metodo", "status", "Nome do cliente");
    var paymentEntityList = List.of(Payment.builder()
            .id("1")
            .clientKey("11111111111")
            .status("status")
            .paymentMethod("metodo")
            .description("descricao")
            .value("100")
            .build());

    when(paymentRepository.findByClientKey(anyString())).thenReturn(paymentEntityList);

    //Act
    var response = getPaymentsByClient.execute("11111111111");

    //Assert
    assertThat(response)
            .isNotEmpty()
            .hasSize(1);

  }

}