package com.fiap.hackathon_payment_management.adapters.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.fiap.hackathon_payment_management.adapters.validation.exception.ValidationException;
import com.fiap.hackathon_payment_management.domain.entity.Payment;
import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;

class ValidationTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GetPaymentsByClient getPaymentsByClient;

    private List<PaymentValidation> paymentValidations;

    private List<PaymentValidation> paymentValidations2;

    public ValidationTest() {
        this.paymentValidations = new ArrayList<>();
        this.paymentValidations2 = new ArrayList<>();
        this.paymentValidations.add(new ValidateCreditCard(restTemplate, getPaymentsByClient));
        this.paymentValidations.add(new ValidatePaymentValue());
        this.paymentValidations2.add(new ValidatePaymentValue());
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void paymentValidations_CPF_Invalid_Test() {

        var requestDto = new PaymentRequestDto("69919462063x", "5465 1946 1186 2986", "01/2026", "542", "1.2");

        assertThrows(ValidationException.class,
                () -> paymentValidations.forEach(validator -> validator.validate(requestDto)),
                "Cartão de crédito não pertence a esse cliente.");

    }

    @Test
    void paymentValidations_CARD_NUMBER_Invalid_Test() {

        var requestDto = new PaymentRequestDto("69919462063", "5465 1946 1186 2986X", "01/2026", "542", "1.2");

        assertThrows(ValidationException.class,
                () -> paymentValidations.forEach(validator -> validator.validate(requestDto)),
                "Cartão de crédito não pertence a esse cliente.");

    }

    @Test
    void paymentValidations_DATE_Invalid_Test() {

        var requestDto = new PaymentRequestDto("69919462063", "5465 1946 1186 2986", "X01/01/2026", "542", "1.2");

        assertThrows(ValidationException.class,
                () -> paymentValidations.forEach(validator -> validator.validate(requestDto)),
                "Cartão de crédito não pertence a esse cliente.");

    }

    @Test
    void paymentValidations_DATE_2_Invalid_Test() {

        var requestDto = new PaymentRequestDto("69919462065", "5465 1946 1186 2985", "01/2020", "542", "1.2");

        assertThrows(ValidationException.class,
                () -> paymentValidations.forEach(validator -> validator.validate(requestDto)),
                "Cartão de crédito não pertence a esse cliente.");

    }

    @Test
    void paymentValidations_DATE_6_Invalid_Test() {

        var requestDto = new PaymentRequestDto("69919462069", "5465 1946 1186 2989", "04/XX/2026", "542", "1.2");

        assertThrows(ValidationException.class,
                () -> paymentValidations.forEach(validator -> validator.validate(requestDto)),
                "Cartão de crédito não pertence a esse cliente.");

    }

    @Test
    void paymentValidations_CVV_Invalid_Test() {

        var requestDto = new PaymentRequestDto("69919462063", "5465 1946 1186 2986", "01/01/2026", "542X", "1.2");

        assertThrows(ValidationException.class,
                () -> paymentValidations.forEach(validator -> validator.validate(requestDto)),
                "Cartão de crédito não pertence a esse cliente.");

    }

    @Test
    void paymentValidations_VALUE_NEGATIVE_Invalid_Test() {

        List<PaymentDto> pagamentos = Arrays.asList(
                new PaymentDto(new Payment("01", "69919462063", "100", "Descricao Teste", "metodo",
                        "status")),
                new PaymentDto(new Payment("01", "69919462063", "100", "Descricao Teste", "metodo",
                        "status")));

        // When
        when(getPaymentsByClient.execute("69919462063")).thenReturn(pagamentos);

        var requestDto = new PaymentRequestDto("69919462063", "5465 1946 1186 2986", "01/2026", "542", "-1.2");

        assertThrows(ValidationException.class,
                () -> paymentValidations2.forEach(validator -> validator.validate(requestDto)),
                "Cartão de crédito não pertence a esse cliente.");

    }
}
