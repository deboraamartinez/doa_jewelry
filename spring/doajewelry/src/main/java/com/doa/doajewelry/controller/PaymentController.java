package com.doa.doajewelry.controller;

import com.doa.doajewelry.dtos.PaymentDTO;
import com.doa.doajewelry.entities.enums.PaymentStatus;
import com.doa.doajewelry.services.PaymentService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing Payments.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    /**
     * Creates a new payment.
     * @param dto the payment data.
     * @return the created PaymentDTO.
     */
    @PostMapping
    public PaymentDTO create(@RequestBody PaymentDTO dto) {
        return service.createPayment(dto);
    }

    /**
     * Retrieves all payments.
     * @return a list of PaymentDTOs.
     */
    @GetMapping
    public List<PaymentDTO> getAll() {
        return service.getAllPayments();
    }

    /**
     * Retrieves a payment by its ID.
     * @param id the payment ID.
     * @return the corresponding PaymentDTO.
     */
    @GetMapping("/{id}")
    public PaymentDTO getOne(@PathVariable Long id) {
        return service.getPayment(id);
    }

    /**
     * Updates an existing payment.
     * @param id the payment ID.
     * @param dto the updated payment data.
     * @return the updated PaymentDTO.
     */
    @PutMapping("/{id}")
    public PaymentDTO update(@PathVariable Long id, @RequestBody PaymentDTO dto) {
        return service.updatePayment(id, dto);
    }

    /**
     * Deletes a payment by its ID.
     * @param id the payment ID.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deletePayment(id);
    }

    /**
     * Refunds a completed payment.
     * @param id the payment ID to refund.
     * @return the refunded PaymentDTO.
     */
    @PostMapping("/{id}/refund")
    public PaymentDTO refund(@PathVariable Long id) {
        return service.refundPayment(id);
    }

    /**
     * Retrieves all payments with a specific status.
     * @param status the payment status to filter by.
     * @return a list of PaymentDTOs with the specified status.
     */
    @GetMapping("/status/{status}")
    public List<PaymentDTO> getByStatus(@PathVariable PaymentStatus status) {
        return service.getAllPaymentsByStatus(status);
    }
}
