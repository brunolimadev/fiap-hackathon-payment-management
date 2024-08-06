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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@Component
public class ValidateCreditCard implements PaymentValidation {

    @Value("${ms-credit-card.url}")
    private String urlGetCreditCard;

    private final RestTemplate restTemplate;

    private final GetPaymentsByClient getPaymentsByClient;

    public ValidateCreditCard(
            RestTemplate restTemplate,
            GetPaymentsByClient getPaymentsByClient) {
        this.restTemplate = restTemplate;
        this.getPaymentsByClient = getPaymentsByClient;
    }

    @Override
    public void validate(PaymentRequestDto requestDto) {

        // var creditCard = getCreditCardByNumber(requestDto.numero());

        String url = new StringBuilder().append(urlGetCreditCard).append("/").append(requestDto.numero()).toString();

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

        if (isDateBeforeCurrentDate(creditCard.data_validade())) {
            throw new ValidationException("Data de validade fora do prazo.");
        }

        if (reachedCardLimit(creditCard, requestDto.valor())) {
            throw new ValidationLimitCardException("Atingiu o limite do cartão de crédito.");
        }

    }

    private boolean reachedCardLimit(CreditCardResponseDto creditCard, String currentPaymentValue) {
        var listPayment = getPaymentsByClient.execute(creditCard.cpf());

        if (listPayment.isEmpty()) {
            return false;
        }

        var totalPaymentAmount = new BigDecimal(currentPaymentValue);
        var totalLimitCredit = new BigDecimal(creditCard.limit());

        for (PaymentDto payment : listPayment) {
            var value = new BigDecimal(payment.getValor());
            totalPaymentAmount = totalPaymentAmount.add(value);
        }

        return totalPaymentAmount.compareTo(totalLimitCredit) > 0;
    }

    private CreditCardResponseDto getCreditCardByNumber(String numberCreditCard) {

        List<CreditCardResponseDto> listMock = Arrays.asList(
                new CreditCardResponseDto("", "69919462065", "1000", "5465 1946 1186 2985", "01/2020", "542"),
                new CreditCardResponseDto("", "69919462063", "1000", "5465 1946 1186 2986", "01/2026", "542"),
                new CreditCardResponseDto("", "69919462069", "1000", "5465 1946 1186 2989", "04/XX/2026", "542"));

        return listMock.stream().filter(dto -> dto.numero().equals(numberCreditCard)).findFirst()
                .orElse(new CreditCardResponseDto("", "69919462063", "1000", "5465 1946 1186 2986", "01/2026", "542"));

        // ResponseEntity<CreditCardResponseDto> response = restTemplate.getForEntity(
        // String.format("%s/{numero}", urlGetCreditCard),
        // CreditCardResponseDto.class,
        // numberCreditCard
        // );
        //
        // if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        // throw new ValidationException("Erro ao realizar busca do cartão de credito");
        // }
        //
        // if (response.getBody() == null) {
        // throw new ValidationException("Cartão de créditdo não encontrado!");
        // }
        //
        // return response.getBody();
    }

    private static boolean isDateBeforeCurrentDate(String cardValidateDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        try {
            LocalDate cardDate = LocalDate.parse(("01/").concat(cardValidateDate), formatter);
            LocalDate currentDate = LocalDate.now();
            return cardDate.isBefore(currentDate);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Data no formato inválido. Use MM/yyyy.");
        }
    }

}
