package com.fiap.hackathon_payment_management.adapters.gateway;

import com.fiap.hackathon_payment_management.domain.entity.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureDataMongo
@Transactional
@ActiveProfiles("test")
class PaymentRepositoryImplTest {

    @Autowired
    private PaymentRepositoryImpl paymentRepository;

//    @Test
    public void testFindByClientKey() {

        List<Payment> pagamentosCliente1 = paymentRepository.findByClientKey("01");
        List<Payment> pagamentosCliente2 = paymentRepository.findByClientKey("02");

        assertNotNull(pagamentosCliente1);
        assertEquals(2, pagamentosCliente1.size());
        assertEquals("01", pagamentosCliente1.get(0).getClientKey());
        assertEquals("01", pagamentosCliente1.get(1).getClientKey());

        assertNotNull(pagamentosCliente2);
        assertEquals(1, pagamentosCliente2.size());
        assertEquals("02", pagamentosCliente2.get(0).getClientKey());

    }

}