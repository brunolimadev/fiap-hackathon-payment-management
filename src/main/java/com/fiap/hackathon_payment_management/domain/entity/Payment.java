package com.fiap.hackathon_payment_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Builder
@Document("payment")
public class Payment {

    @Id
    private String id;

    @DBRef
    private Client client;

    private String value;
    private String description;
    private String paymentMethod;
    private String status;
}

