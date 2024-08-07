package com.fiap.hackathon_payment_management.adapters.validation;

import com.fiap.hackathon_payment_management.adapters.validation.exception.ValidationException;
import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.dto.CreditCardResponseDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import com.fiap.hackathon_payment_management.utils.ConvertEntityToDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ValidateCreditCardTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GetPaymentsByClient getPaymentsByClient;

    private ValidateCreditCard validateCreditCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validateCreditCard = new ValidateCreditCard(restTemplate, getPaymentsByClient);
    }

    @Test
    void validate_ValidPaymentRequestDto_NoExceptionThrown() {
        // Arrange
        String cpf = "69919462063";
        String numero = "5465 1946 1186 2986";
        String cvv = "542";
        String dataValidade = "01/26";
        String valor = "1.2";

        PaymentRequestDto requestDto = new PaymentRequestDto(cpf, numero, dataValidade, cvv, valor);

        CreditCardResponseDto creditCard = new CreditCardResponseDto("", cpf, "1000", numero, dataValidade, cvv);

        ResponseEntity<CreditCardResponseDto> responseEntity = new ResponseEntity<>(creditCard, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(CreditCardResponseDto.class))).thenReturn(responseEntity);

        // Act & Assert
        assertDoesNotThrow(() -> validateCreditCard.validate(requestDto));
    }

    @Test
    void validate_InvalidCpf_ValidationExceptionThrown() {
        // Arrange
        String cpf = "69919462063";
        String numero = "5465 1946 1186 2986";
        String cvv = "542";
        String dataValidade = "01/2026";
        String valor = "1.2";

        PaymentRequestDto requestDto = new PaymentRequestDto(cpf, numero, cvv, dataValidade, valor);

        CreditCardResponseDto creditCard = new CreditCardResponseDto("", numero, "1000", numero, dataValidade, cvv);

        ResponseEntity<CreditCardResponseDto> responseEntity = new ResponseEntity<>(creditCard, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(CreditCardResponseDto.class))).thenReturn(responseEntity);

        // Act & Assert
        assertThrows(ValidationException.class, () -> validateCreditCard.validate(requestDto));

        // Verify
        verify(getPaymentsByClient, never()).execute(anyString());
    }

    @Test
    void validate_InvalidNumero_ValidationExceptionThrown() {
        // Arrange
        String cpf = "69919462063";
        String numero = "5465 1946 1186 2986";
        String cvv = "542";
        String dataValidade = "01/2026";
        String valor = "1.2";

        PaymentRequestDto requestDto = new PaymentRequestDto(cpf, numero, cvv, dataValidade, valor);

        CreditCardResponseDto creditCard = new CreditCardResponseDto("", "1234567890", "1000", numero, dataValidade,
                cvv);

        ResponseEntity<CreditCardResponseDto> responseEntity = new ResponseEntity<>(creditCard, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(CreditCardResponseDto.class))).thenReturn(responseEntity);

        // Act & Assert
        assertThrows(ValidationException.class, () -> validateCreditCard.validate(requestDto));

        // Verify
        verify(getPaymentsByClient, never()).execute(anyString());
    }

    @Test
    void validate_InvalidCvv_ValidationExceptionThrown() {
        // Arrange
        String cpf = "69919462063";
        String numero = "5465 1946 1186 2986";
        String cvv = "542";
        String dataValidade = "01/2026";
        String valor = "1.2";

        PaymentRequestDto requestDto = new PaymentRequestDto(cpf, numero, cvv, dataValidade, valor);

        CreditCardResponseDto creditCard = new CreditCardResponseDto("", numero, "1000", numero, dataValidade, "123");

        ResponseEntity<CreditCardResponseDto> responseEntity = new ResponseEntity<>(creditCard, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(CreditCardResponseDto.class))).thenReturn(responseEntity);

        // Act & Assert
        assertThrows(ValidationException.class, () -> validateCreditCard.validate(requestDto));

        // Verify
        verify(getPaymentsByClient, never()).execute(anyString());
    }

    @Test
    void validate_InvalidDataValidade_ValidationExceptionThrown() {
        // Arrange
        String cpf = "69919462063";
        String numero = "5465 1946 1186 2986";
        String cvv = "542";
        String dataValidade = "01/2026";
        String valor = "1.2";

        PaymentRequestDto requestDto = new PaymentRequestDto(cpf, numero, cvv, dataValidade, valor);

        CreditCardResponseDto creditCard = new CreditCardResponseDto("", numero, "1000", numero, "01/2022", cvv);

        ResponseEntity<CreditCardResponseDto> responseEntity = new ResponseEntity<>(creditCard, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(CreditCardResponseDto.class))).thenReturn(responseEntity);

        // Act & Assert
        assertThrows(ValidationException.class, () -> validateCreditCard.validate(requestDto));

        // Verify
        verify(getPaymentsByClient, never()).execute(anyString());
    }

    @Test
    void validate_DateBeforeCurrentDate_ValidationExceptionThrown() {
        // Arrange
        String cpf = "69919462063";
        String numero = "5465 1946 1186 2986";
        String cvv = "542";
        String dataValidade = "01/2026";
        String valor = "1.2";

        PaymentRequestDto requestDto = new PaymentRequestDto(cpf, numero, cvv, dataValidade, valor);

        CreditCardResponseDto creditCard = new CreditCardResponseDto("", numero, "1000", numero, "01/2010", cvv);

        ResponseEntity<CreditCardResponseDto> responseEntity = new ResponseEntity<>(creditCard, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(CreditCardResponseDto.class))).thenReturn(responseEntity);

        // Act & Assert
        assertThrows(ValidationException.class, () -> validateCreditCard.validate(requestDto));

        // Verify
        verify(getPaymentsByClient, never()).execute(anyString());
    }

    @Test
    void validate_ReachedCardLimit_ValidationLimitCardExceptionThrown() {
        // Arrange
        String cpf = "69919462063";
        String numero = "5465 1946 1186 2986";
        String cvv = "542";
        String dataValidade = "01/2026";
        String valor = "1.2";

        PaymentRequestDto requestDto = new PaymentRequestDto(cpf, numero, cvv, dataValidade, valor);

        Payment payment1 = new Payment(dataValidade, numero, valor, cvv, dataValidade, valor);
        Payment payment2 = new Payment(dataValidade, "5465 9034 1186 2986", valor, cvv, dataValidade, "200");

        List<PaymentDto> payments = Arrays.asList(ConvertEntityToDto.convert(payment1), ConvertEntityToDto.convert(payment2));

        when(getPaymentsByClient.execute(cpf)).thenReturn(payments);

        CreditCardResponseDto creditCard = new CreditCardResponseDto("", numero, "200", numero, dataValidade, cvv);

        ResponseEntity<CreditCardResponseDto> responseEntity = new ResponseEntity<>(creditCard, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(CreditCardResponseDto.class))).thenReturn(responseEntity);

        // Act & Assert
        assertThrows(ValidationException.class, () -> validateCreditCard.validate(requestDto));
    }

    @Test
    void validate_NotReachedCardLimit_NoExceptionThrown() {
        // Arrange
        String cpf = "69919462063";
        String numero = "5465 1946 1186 2986";
        String cvv = "542";
        String dataValidade = "01/26";
        String valor = "1.2";

        PaymentRequestDto requestDto = new PaymentRequestDto(cpf, numero, dataValidade, cvv, valor);

        Payment payment1 = new Payment(dataValidade, "69919462063", valor, cvv, dataValidade, valor);
        Payment payment2 = new Payment(dataValidade, "69919462063", valor, cvv, dataValidade, "200");

        List<PaymentDto> payments = Arrays.asList(
                ConvertEntityToDto.convertAllValues(payment1),
                ConvertEntityToDto.convertAllValues(payment2));
        when(getPaymentsByClient.execute(cpf)).thenReturn(payments);

        CreditCardResponseDto creditCard = new CreditCardResponseDto("", "69919462063", "300", numero, dataValidade,
                cvv);

        ResponseEntity<CreditCardResponseDto> responseEntity = new ResponseEntity<>(creditCard, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(CreditCardResponseDto.class))).thenReturn(responseEntity);

        // Act & Assert
        assertDoesNotThrow(() -> validateCreditCard.validate(requestDto));

    }
}