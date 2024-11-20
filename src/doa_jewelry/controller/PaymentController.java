package doa_jewelry.controller;

import doa_jewelry.entity.Payment;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.service.PaymentService;

import java.util.List;

// Controller to handle payment-related operations
public class PaymentController {
    private final PaymentService paymentService;

    // Constructor to initialize the PaymentService dependency
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Create a new payment and handle potential exceptions
    public Payment createPayment(Payment payment) {
        try {
            return paymentService.createPayment(payment); // Call the service to create the payment
        } catch (RepositoryException e) {
            System.err.println("Error creating payment: " + e.getMessage());
            return null;
        }
    }

    // Retrieve a payment by its ID and handle potential exceptions
    public Payment getPaymentById(Long id) {
        try {
            return paymentService.getPaymentById(id); // Call the service to get the payment
        } catch (RepositoryException e) {
            System.err.println("Error retrieving payment: " + e.getMessage());
            return null;
        }
    }

    // Retrieve all payments and handle potential exceptions
    public List<Payment> getAllPayments() {
        try {
            return paymentService.getAllPayments(); // Call the service to get all payments
        } catch (Exception e) {
            System.err.println("Error retrieving payments: " + e.getMessage());
            return List.of(); // Return an empty list if an error occurs
        }
    }

    // Delete a payment by its ID and handle potential exceptions
    public void deletePayment(Long id) {
        try {
            paymentService.deletePayment(id); // Call the service to delete the payment
        } catch (RepositoryException e) {
            System.err.println("Error deleting payment: " + e.getMessage());
        }
    }

    // Update an existing payment and handle potential exceptions
    public Payment updatePayment(Payment payment) {
        try {
            return paymentService.updatePayment(payment); // Call the service to update the payment
        } catch (RepositoryException e) {
            System.err.println("Error updating payment: " + e.getMessage());
            return null;
        }
    }

    // Save all payments to the storage and handle potential exceptions
    public void saveAll() {
        try {
            paymentService.saveAll(); // Call the service to save all payments
        } catch (RepositoryException e) {
            System.err.println("Error saving payments: " + e.getMessage());
        }
    }
}
