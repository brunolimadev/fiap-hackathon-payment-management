package com.fiap.hackathon_payment_management.adapter.inbound.controllers;

import com.fiap.hackathon_payment_management.domain.entities.Payment;
import com.fiap.hackathon_payment_management.domain.exceptions.PaymentNotFundException;
import com.fiap.hackathon_payment_management.domain.ports.inbound.FindPaymentUserCasePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PaymentController {

    private final FindPaymentUserCasePort findPaymentUserCasePort;

    public PaymentController(FindPaymentUserCasePort findPaymentUserCasePort) {
        this.findPaymentUserCasePort = findPaymentUserCasePort;
    }

    @GetMapping("/cliente/{key}")
    public ResponseEntity<?> getPaymentsByClientKey(@RequestParam String key) throws PaymentNotFundException {

        List<Payment> payments = findPaymentUserCasePort.execute(key);

        return null;
    }

}
