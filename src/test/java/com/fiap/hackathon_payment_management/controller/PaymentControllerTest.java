package com.fiap.hackathon_payment_management.controller;

import com.fiap.hackathon_payment_management.adapters.controller.PaymentController;
import com.fiap.hackathon_payment_management.adapters.validation.PaymentValidation;
import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.SavePayment;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import com.fiap.hackathon_payment_management.utils.ConvertEntityToDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PaymentControllerTest {

    @Mock
    private GetPaymentsByClient getPaymentsByClient;

    @Mock
    private SavePayment savePayment;

    @Mock
    private List<PaymentValidation> validations;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPagamentosPorCliente_Success() {
        // Given
        String clientId = "cliente1";
        List<PaymentDto> pagamentos = Arrays.asList(
                ConvertEntityToDto.convert(new Payment("01", "02", 100.00, "Descricao Teste", "metodo",
                        "status")),
                ConvertEntityToDto.convert(new Payment("01", "02", 100.00, "Descricao Teste", "metodo",
                        "status"))
        );

        // When
        when(getPaymentsByClient.execute(clientId)).thenReturn(pagamentos);
        ResponseEntity<List<PaymentDto>> response = paymentController.getPaymentsByClient(clientId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentos, response.getBody());
    }

    @Test
    void testGetPagamentosPorCliente_EmptyList() {
        // Given
        String clienteId = "cliente1";
        List<PaymentDto> pagamentos = Arrays.asList();

        // When
        when(getPaymentsByClient.execute(clienteId)).thenReturn(pagamentos);

        ResponseEntity<List<PaymentDto>> response = paymentController.getPaymentsByClient(clienteId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentos, response.getBody());
    }

    @Test
    void testGetPagamentosPorCliente_NotFound() {
        // Given
        String clienteId = "cliente1";

        // When
        when(getPaymentsByClient.execute(clienteId)).thenReturn(null);
        ResponseEntity<List<PaymentDto>> response = paymentController.getPaymentsByClient(clienteId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testSavePayment_Success() {
        // Given
        var request = new PaymentRequestDto("100", "decricao", "metodo", "status", 100.00);
        var paymentDto = ConvertEntityToDto.convert(Payment.builder()
                .value(100.00)
                .paymentMethod("metodo")
                .description("descricao")
                .status("status")
                .clientKey("02")
                .build());
        // When
        when(savePayment.execute(request)).thenReturn(paymentDto);
        ResponseEntity<?> response = paymentController.savePayment(request);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentDto, response.getBody());
    }

}