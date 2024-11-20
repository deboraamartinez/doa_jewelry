package test.entity;

import doa_jewelry.entity.Payment;
import doa_jewelry.entity.PaymentMethod;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PaymentEntityTest {

    @Test
    public void testPaymentInitialization() {
        Payment payment = new Payment(500.0, LocalDate.now(), PaymentMethod.CREDIT_CARD, 1L);

        assertEquals(500.0, payment.getAmount(), 0.01);
        assertEquals(PaymentMethod.CREDIT_CARD, payment.getMethod());
        assertEquals(Long.valueOf(1L), payment.getOrderId());
        assertNotNull(payment.getDate());
    }
}
