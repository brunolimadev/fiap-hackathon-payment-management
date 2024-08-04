package com.fiap.hackathon_payment_management.controller;

import com.fiap.hackathon_payment_management.adapters.controller.PaymentController;
import com.fiap.hackathon_payment_management.adapters.validation.PaymentValidation;
import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.SavePayment;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
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

public class PaymentControllerTest {

    @Mock
    private GetPaymentsByClient getPaymentsByClient;

    @Mock
    private SavePayment savePayment;

    @Mock
    private List<PaymentValidation> validations;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPagamentosPorCliente_Success() {
        // Given
        String clientId = "cliente1";
        List<PaymentDto> pagamentos = Arrays.asList(
                new PaymentDto(new Payment("01", "02", "100", "Descricao Teste", "metodo",
                        "status")),
                new PaymentDto(new Payment("01", "02", "100", "Descricao Teste", "metodo",
                        "status")));

        // When
        when(getPaymentsByClient.execute(clientId)).thenReturn(pagamentos);
        ResponseEntity<List<PaymentDto>> response = paymentController.getPaymentsByClient(clientId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentos, response.getBody());
    }

    @Test
    public void testGetPagamentosPorCliente_EmptyList() {
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
    public void testGetPagamentosPorCliente_NotFound() {
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
    public void testSavePayment_Success() {
        // Given
        var request = new PaymentRequestDto("100", "decricao", "metodo", "status", "Nome do cliente");
        var resposne = new PaymentDto(Payment.builder()
                .value("100")
                .paymentMethod("metodo")
                .description("descricao")
                .status("status")
                .clientKey("02")
                .build());
        // When
        when(savePayment.execute(request)).thenReturn(resposne);
        ResponseEntity<?> response = paymentController.savePayment(request);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resposne, response.getBody());
    }

}
