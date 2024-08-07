package com.fiap.hackathon_payment_management.adapters.controller;

import com.fiap.hackathon_payment_management.adapters.validation.PaymentValidation;
import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.SavePayment;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pagamentos")
@Tag(name = "Payment Controller", description = "Payment can manage card through API resources")
public class PaymentController {

    private final GetPaymentsByClient getPaymentsByClient;
    private final SavePayment savePayment;
    private final List<PaymentValidation> validations;

    @Autowired
    public PaymentController(
            GetPaymentsByClient getPaymentsByClient,
            SavePayment savePayment,
            List<PaymentValidation> validations
    ) {

        this.getPaymentsByClient = getPaymentsByClient;
        this.savePayment = savePayment;
        this.validations = validations;

    }

    @Operation(summary = "Register payment")
    @ApiResponse(
            responseCode = "200",
            description = "Returns a payment key"
    )
    @PostMapping
    public ResponseEntity<PaymentDto> savePayment(@RequestBody @Valid PaymentRequestDto request) {

        validations.forEach(validator -> validator.validate(request));

        return ResponseEntity.ok(savePayment.execute(request));

    }

    @Operation(summary = "List payments by CPF")
    @ApiResponse(responseCode = "200", description = "Returns a list payments")
    @GetMapping("/clientes/{chave}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByClient(
            @PathVariable(name = "chave") String clientKey
    ) {

        return ResponseEntity.ok(getPaymentsByClient.execute(clientKey));

    }

}