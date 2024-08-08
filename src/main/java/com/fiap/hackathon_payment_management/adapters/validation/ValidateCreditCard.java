package com.fiap.hackathon_payment_management.adapters.validation;

import com.fiap.hackathon_payment_management.adapters.validation.exception.ValidationException;
import com.fiap.hackathon_payment_management.adapters.validation.exception.ValidationLimitCardException;
import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.dto.CreditCardResponseDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class ValidateCreditCard implements PaymentValidation {

    @Value("${ms-credit-card.url}")
    private String urlGetCreditCard;

    private final RestTemplate restTemplate;

    private final GetPaymentsByClient getPaymentsByClient;

    @Autowired
    public ValidateCreditCard(
            RestTemplate restTemplate,
            GetPaymentsByClient getPaymentsByClient) {

        this.restTemplate = restTemplate;
        this.getPaymentsByClient = getPaymentsByClient;

    }

    @Override
    public void validate(PaymentRequestDto requestDto) {

        String url = urlGetCreditCard + "/" + requestDto.numero();

        ResponseEntity<CreditCardResponseDto> result = restTemplate.getForEntity(url,
                CreditCardResponseDto.class);

        var creditCard = result.getBody();

        if (!creditCard.cpf().equals(requestDto.cpf())) {
            throw new ValidationException("Cartão de crédito não pertence a esse cliente.");
        }

        if (!creditCard.numero().equals(requestDto.numero())) {
            throw new ValidationException("Numero do carrtão não corresponde.");
        }

        if (!creditCard.cvv().equals(requestDto.cvv())) {
            throw new ValidationException("Código de verificação não corresponde.");
        }

        if (!creditCard.data_validade().equals(requestDto.data_validade())) {
            throw new ValidationException("Data de validade não corresponde.");
        }

        if (reachedCardLimit(creditCard, requestDto.valor())) {
            throw new ValidationLimitCardException("Atingiu o limite do cartão de crédito.");
        }

    }

    private boolean reachedCardLimit(CreditCardResponseDto creditCard, Double currentPaymentValue) {

        var listPayment = getPaymentsByClient.execute(creditCard.cpf());

        if (listPayment.isEmpty()) {
            return false;
        }

        var totalPaymentAmount = new BigDecimal(currentPaymentValue);
        var totalLimitCredit = new BigDecimal(creditCard.limite());

        for (PaymentDto payment : listPayment) {
            var value = new BigDecimal(payment.getValue());
            totalPaymentAmount = totalPaymentAmount.add(value);
        }

        return totalPaymentAmount.compareTo(totalLimitCredit) > 0;

    }

}