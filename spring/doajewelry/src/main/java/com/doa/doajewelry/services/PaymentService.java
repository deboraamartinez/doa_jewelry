package com.doa.doajewelry.services;

import com.doa.doajewelry.dtos.PaymentDTO;
import com.doa.doajewelry.entities.Order;
import com.doa.doajewelry.entities.Payment;
import com.doa.doajewelry.entities.enums.OrderStatus;
import com.doa.doajewelry.entities.enums.PaymentStatus;
import com.doa.doajewelry.repositories.JewelryRepository;
import com.doa.doajewelry.repositories.OrderRepository;
import com.doa.doajewelry.repositories.PaymentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service layer for managing Payment operations.
 * Handles creation, retrieval, updating, deletion, and refunding of payments.
 */
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final JewelryRepository jewelryRepository;

    /**
     * Constructor-based dependency injection for repositories.
     * 
     * @param paymentRepository Repository for Payment entities.
     * @param orderRepository   Repository for Order entities.
     * @param jewelryRepository Repository for Jewelry entities.
     */
    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository, JewelryRepository jewelryRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.jewelryRepository = jewelryRepository;
    }

    /**
     * Creates a new Payment for an Order.
     * Decrements the stock of associated jewelry items and sets payment status based on the total amount.
     * 
     * @param dto PaymentDTO containing payment details.
     * @return PaymentDTO of the created payment.
     */
    @Transactional
    public PaymentDTO createPayment(PaymentDTO dto) {
        // Retrieve the associated order
        Order order = orderRepository.findById(dto.getOrderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        // Check if the order is already canceled
        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add payment to a canceled order");
        }

        // Calculate the total amount of the order
        Double orderTotal = order.getTotalAmount();

        // Calculate the total amount already paid (COMPLETED payments)
        Double totalPaid = paymentRepository.findAllByOrderId(order.getId()).stream()
            .filter(p -> p.getStatus() == PaymentStatus.COMPLETED)
            .mapToDouble(Payment::getAmount)
            .sum();

        // Check if the new payment exceeds the order total
        if (totalPaid + dto.getAmount() > orderTotal) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment exceeds the total order amount");
        }

        // Determine the payment status based on the remaining amount
        PaymentStatus status = (totalPaid + dto.getAmount() < orderTotal) ? PaymentStatus.PENDING : PaymentStatus.COMPLETED;

        // Create the Payment entity
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setDate(dto.getDate() == null ? LocalDate.now() : dto.getDate()); // Set current date if not provided
        payment.setMethod(dto.getMethod());
        payment.setOrder(order);
        payment.setStatus(status);

        // Save the payment to the database
        Payment savedPayment = paymentRepository.save(payment);

        // Update the order status based on payment status
        if (status == PaymentStatus.COMPLETED) {
            order.setStatus(OrderStatus.ACCEPTED);
        } else {
            order.setStatus(OrderStatus.PENDING);
        }
        orderRepository.save(order); // Persist the updated order status

        // Convert the saved Payment entity to PaymentDTO using the static method
        return PaymentDTO.fromEntity(savedPayment);
    }

    /**
     * Retrieves all Payments from the database.
     * 
     * @return List of PaymentDTOs representing all payments.
     */
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
            .map(PaymentDTO::fromEntity) // Converts each Payment entity to DTO using the static method
            .collect(Collectors.toList());
    }

    /**
     * Retrieves all Payments associated with a specific Order ID.
     * 
     * @param orderId The ID of the order whose payments are to be retrieved.
     * @return List of PaymentDTOs associated with the specified order.
     */
    public List<PaymentDTO> getAllByOrderId(Long orderId) {
        List<Payment> payments = paymentRepository.findAllByOrderId(orderId);
        if (payments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payments not found for order ID " + orderId);
        }
        return payments.stream()
                       .map(PaymentDTO::fromEntity) // Converts each Payment entity to DTO
                       .collect(Collectors.toList());
    }

    /**
     * Retrieves a single Payment by ID.
     * 
     * @param id The ID of the payment to retrieve.
     * @return PaymentDTO of the found payment.
     */
    public PaymentDTO getPayment(Long id) {
        Payment p = paymentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        return PaymentDTO.fromEntity(p); // Converts the Payment entity to DTO
    }

    /**
     * Updates an existing Payment.
     * Adjusts the stock based on the new status and ensures payment does not exceed order total.
     * 
     * @param id  The ID of the payment to update.
     * @param dto PaymentDTO containing updated payment details.
     * @return PaymentDTO of the updated payment.
     */
    @Transactional
    public PaymentDTO updatePayment(Long id, PaymentDTO dto) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        // Update basic fields
        payment.setAmount(dto.getAmount());
        payment.setDate(dto.getDate() == null ? LocalDate.now() : dto.getDate()); // Set current date if not provided
        payment.setMethod(dto.getMethod());

        // Retrieve the associated order
        Order order = payment.getOrder();
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment is not associated with any order");
        }

        // Calculate the total amount already paid (excluding this payment)
        Double orderTotal = order.getTotalAmount();
        Double totalPaid = paymentRepository.findAllByOrderId(order.getId()).stream()
            .filter(p -> !p.getId().equals(id) && p.getStatus() == PaymentStatus.COMPLETED)
            .mapToDouble(Payment::getAmount)
            .sum();

        // Check if the new payment exceeds the order total
        if (totalPaid + dto.getAmount() > orderTotal) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment exceeds the total order amount");
        }

        // Determine the new status based on the updated total
        PaymentStatus newStatus = (totalPaid + dto.getAmount() < orderTotal) ? PaymentStatus.PENDING : PaymentStatus.COMPLETED;

        if (payment.getStatus() != newStatus) {
            // If changing from COMPLETED to PENDING, update order status accordingly
            if (newStatus == PaymentStatus.PENDING && payment.getStatus() == PaymentStatus.COMPLETED) { 
                order.setStatus(OrderStatus.PENDING);
            }
            // If changing from PENDING to COMPLETED, update order status accordingly
            else if (newStatus == PaymentStatus.COMPLETED && payment.getStatus() == PaymentStatus.PENDING) {
                order.setStatus(OrderStatus.ACCEPTED);
            }

            // Update the payment status
            payment.setStatus(newStatus);
            orderRepository.save(order); // Persist the updated order status
        }

        // Save the updated payment to the database
        Payment savedPayment = paymentRepository.save(payment);

        // Convert the saved Payment entity to PaymentDTO using the static method
        return PaymentDTO.fromEntity(savedPayment);
    }

    /**
     * Deletes a Payment by ID.
     * Restores the stock and updates the order status if necessary.
     * 
     * @param id The ID of the payment to delete.
     */
    @Transactional
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        Order order = payment.getOrder();
        if (order != null) {
            if (payment.getStatus() == PaymentStatus.PENDING || payment.getStatus() == PaymentStatus.COMPLETED) {
                // Update order status based on remaining payments
                Double totalPaid = paymentRepository.findAllByOrderId(order.getId()).stream()
                    .filter(p -> p.getStatus() == PaymentStatus.COMPLETED)
                    .mapToDouble(Payment::getAmount)
                    .sum();

                if (totalPaid > 0) {
                    order.setStatus(OrderStatus.PENDING);
                } else {
                    order.setStatus(OrderStatus.PENDING); // Adjust as per business logic
                }
                orderRepository.save(order); // Persist the updated order status
            }
        }

        // Delete the payment from the database
        paymentRepository.delete(payment);
    }

    /**
     * Refunds a completed payment.
     * Sets the payment status to REFUNDED and adjusts the order status accordingly.
     * 
     * @param id The ID of the payment to refund.
     * @return PaymentDTO of the refunded payment.
     */
    @Transactional
    public PaymentDTO refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        // Verify that the payment is COMPLETED before refunding
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only COMPLETED payments can be refunded");
        }

        // Set status to REFUNDED
        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment); // Persist the updated payment status

        return PaymentDTO.fromEntity(payment); // Convert to DTO and return
    }

    /**
     * Retrieves all Payments with a specific status.
     * 
     * @param status The status of the payments to retrieve.
     * @return List of PaymentDTOs matching the specified status.
     */
    public List<PaymentDTO> getAllPaymentsByStatus(PaymentStatus status) {
        List<Payment> payments = paymentRepository.findAllByStatus(status); // Fetch payments with the given status
        return payments.stream()
            .map(PaymentDTO::fromEntity) // Convert each Payment entity to DTO
            .collect(Collectors.toList());
    }
}
