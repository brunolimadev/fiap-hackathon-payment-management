package com.fiap.hackathon_payment_management.domain.exceptions;

public class PaymentNotFundException extends RuntimeException {
    public PaymentNotFundException(String message) {
        super(message);
    }
}
