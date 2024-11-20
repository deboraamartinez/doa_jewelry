package test.repository;


import doa_jewelry.entity.Payment;
import doa_jewelry.entity.PaymentMethod;
import doa_jewelry.repository.PaymentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @Before
    public void setUp() {
        paymentRepository = new PaymentRepository();
        paymentRepository.deleteAll(); 
    }

    @After
    public void tearDown() {
        paymentRepository.deleteAll(); 
    }

    @Test
    public void testSavePayment() {
        Payment payment = new Payment(500.0, LocalDate.now(), PaymentMethod.CREDIT_CARD, 1L);

        Payment savedPayment = paymentRepository.save(payment);

        assertNotNull(savedPayment.getId());
        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    public void testFindById() {
        Payment payment = new Payment(500.0, LocalDate.now(), PaymentMethod.CREDIT_CARD, 1L);

        Payment savedPayment = paymentRepository.save(payment);
        Payment retrievedPayment = paymentRepository.findById(savedPayment.getId()).orElse(null);

        assertNotNull(retrievedPayment);
        assertEquals(savedPayment.getId(), retrievedPayment.getId());
    }

    @Test
    public void testDeletePayment() {
        Payment payment = new Payment(500.0, LocalDate.now(), PaymentMethod.CREDIT_CARD, 1L);

        Payment savedPayment = paymentRepository.save(payment);
        paymentRepository.deleteById(savedPayment.getId());

        assertTrue(paymentRepository.findAll().isEmpty());
    }
}
