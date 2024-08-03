package com.fiap.hackathon_payment_management.config;

import com.fiap.hackathon_payment_management.domain.repository.ClientRepository;
import com.fiap.hackathon_payment_management.domain.repository.PaymentRepository;
import com.fiap.hackathon_payment_management.usecase.GetPaymentsByClient;
import com.fiap.hackathon_payment_management.usecase.SavePayment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public GetPaymentsByClient getPaymentsByClient(PaymentRepository paymentRepository) {
        return new GetPaymentsByClient(paymentRepository);
    }

    @Bean
    public SavePayment savePayment(PaymentRepository paymentRepository, ClientRepository clientRepository) {
        return new SavePayment(paymentRepository, clientRepository);
    }
}
