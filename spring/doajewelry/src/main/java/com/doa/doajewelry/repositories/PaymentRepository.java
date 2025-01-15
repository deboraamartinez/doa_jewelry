package com.doa.doajewelry.repositories;

import com.doa.doajewelry.entities.Payment;
import com.doa.doajewelry.entities.enums.PaymentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing Payment entities.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /**
     * Finds all payments associated with a specific order ID.
     * @param orderId the ID of the order.
     * @return a list of payments.
     */
    List<Payment> findAllByOrderId(Long orderId);

    /**
     * Finds all payments with a specific status.
     * @param status the status of the payment.
     * @return a list of payments.
     */
    List<Payment> findAllByStatus(PaymentStatus status);
}
