package doa_jewelry.service;

import doa_jewelry.entity.Order;
import doa_jewelry.entity.OrderStatus;
import doa_jewelry.entity.Payment;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.exception.InvalidPaymentException;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.repository.OrderRepository;
import doa_jewelry.repository.PaymentRepository;

import java.util.List;
import java.util.stream.Collectors;

// Service responsible for handling business logic related to payments
public class PaymentService {

    private final PaymentRepository paymentRepository; // Repository to manage payment data
    private final OrderRepository orderRepository; // Repository to access and manage orders

    // Constructor to initialize the service with necessary repositories
    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // Creates a new payment and validates if it respects the order's total amount
    public Payment createPayment(Payment payment) throws RepositoryException {
        // Check if the order associated with the payment exists
        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + payment.getOrderId()));

        // Calculate the total amount already paid for the order
        double totalPaid = getTotalPaidForOrder(payment.getOrderId());

        // Validate that the new payment doesn't exceed the order's total amount
        if (totalPaid + payment.getAmount() > order.getTotalAmount()) {
            throw new InvalidPaymentException("Payment exceeds the order's total amount.");
        }

        // Save the payment in the repository
        Payment savedPayment = paymentRepository.save(payment);

        // Update the total paid
        totalPaid += payment.getAmount();

        // If the total paid equals or exceeds the order's total amount, update its status to ACCEPTED
        if (totalPaid >= order.getTotalAmount()) {
            order.setStatus(OrderStatus.ACCEPTED);
        }

        // Save the updated order
        orderRepository.update(order);

        return savedPayment;
    }

    // Retrieves a payment by its ID
    public Payment getPaymentById(Long id) throws RepositoryException {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
    }

    // Retrieves all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Deletes a payment and updates the associated order's status accordingly
    public void deletePayment(Long id) throws RepositoryException {
        // Retrieve the payment to be deleted
        Payment payment = getPaymentById(id);

        // Delete the payment from the repository
        paymentRepository.deleteById(id);

        // Recalculate the total paid for the order
        double totalPaid = getTotalPaidForOrder(payment.getOrderId());

        // Retrieve the associated order
        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + payment.getOrderId()));

        // Update the order's status based on the new total paid
        if (totalPaid < order.getTotalAmount()) {
            order.setStatus(OrderStatus.PENDING);
        }

        // Save the updated order
        orderRepository.update(order);
    }

    // Updates an existing payment and ensures it respects the order's total amount
    public Payment updatePayment(Payment payment) throws RepositoryException {
        // Retrieve the existing payment
        Payment existingPayment = getPaymentById(payment.getId());

        // Calculate the total paid excluding the existing payment's amount
        double totalPaidExcludingCurrent = getTotalPaidForOrder(payment.getOrderId()) - existingPayment.getAmount();

        // Validate that the updated payment doesn't exceed the order's total amount
        if (totalPaidExcludingCurrent + payment.getAmount() > getOrderTotalAmount(payment.getOrderId())) {
            throw new InvalidPaymentException("Updated payment exceeds the order's total amount.");
        }

        // Update the payment in the repository
        Payment updatedPayment = paymentRepository.update(payment);

        // Recalculate the total paid after the update
        double totalPaid = totalPaidExcludingCurrent + payment.getAmount();

        // Retrieve the associated order
        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + payment.getOrderId()));

        // Update the order's status based on the new total paid
        if (totalPaid >= order.getTotalAmount()) {
            order.setStatus(OrderStatus.ACCEPTED);
        } else {
            order.setStatus(OrderStatus.PENDING);
        }

        // Save the updated order
        orderRepository.update(order);

        return updatedPayment;
    }

    // Retrieves all payments associated with a specific order
    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findAll().stream()
                .filter(p -> p.getOrderId().equals(orderId))
                .collect(Collectors.toList());
    }

    // Calculates the total amount paid for a specific order
    private double getTotalPaidForOrder(Long orderId) {
        return getPaymentsByOrderId(orderId).stream()
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    // Retrieves the total amount of an order
    private double getOrderTotalAmount(Long orderId) throws RepositoryException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));
        return order.getTotalAmount();
    }

    // Saves all payments to persistent storage
    public void saveAll() throws RepositoryException {
        paymentRepository.saveAll();
    }
}
