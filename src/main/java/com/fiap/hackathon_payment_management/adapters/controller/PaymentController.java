package com.fiap.hackathon_payment_management.adapters.controller;

import com.fiap.hackathon_payment_management.adapters.validation.PaymentValidation;
import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.SavePayment;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PaymentController {

    private final GetPaymentsByClient getPaymentsByClient;
    private final SavePayment savePayment;
    private final List<PaymentValidation> validations;

    @Autowired
    public PaymentController(GetPaymentsByClient getPaymentsByClient, SavePayment savePayment, List<PaymentValidation> validations) {
        this.getPaymentsByClient = getPaymentsByClient;
        this.savePayment = savePayment;
        this.validations = validations;
    }

    @PostMapping
    public ResponseEntity<?> savePayment(@RequestBody @Valid PaymentRequestDto request) {
        validations.forEach((validator) -> validator.validate(request));
        return ResponseEntity.ok(savePayment.execute(request));
    }

    @GetMapping("/clientes/{chave}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByClient(@PathVariable(name = "chave") String clientKey) {
        return ResponseEntity.ok(getPaymentsByClient.execute(clientKey));
    }
}
