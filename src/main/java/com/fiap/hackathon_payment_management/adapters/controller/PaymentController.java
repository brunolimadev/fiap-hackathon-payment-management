package com.fiap.hackathon_payment_management.adapters.controller;

import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.SavePayment;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentDto;
import com.fiap.hackathon_payment_management.usecase.dto.PaymentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PaymentController {

    private final GetPaymentsByClient getPaymentsByClient;
    private final SavePayment savePayment;

    @Autowired
    public PaymentController(GetPaymentsByClient getPaymentsByClient, SavePayment savePayment) {
        this.getPaymentsByClient = getPaymentsByClient;
        this.savePayment = savePayment;
    }

    @PostMapping
    public ResponseEntity<?> savePayment(@RequestBody PaymentRequestDto request) {
        return ResponseEntity.ok(savePayment.execute(request));
    }

    @GetMapping("/clientes/{chave}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByClient(@PathVariable(name = "chave") String clientKey) {
        return ResponseEntity.ok(getPaymentsByClient.execute(clientKey));
    }
}
