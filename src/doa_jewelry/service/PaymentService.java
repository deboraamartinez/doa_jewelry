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

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Payment createPayment(Payment payment) throws RepositoryException {
        // Check if associated order exists
        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + payment.getOrderId()));

        // Calculate the total amount already paid
        double totalPaid = getTotalPaidForOrder(payment.getOrderId());

        // Check if the new payment would exceed the order's total amount
        if (totalPaid + payment.getAmount() > order.getTotalAmount()) {
            throw new InvalidPaymentException("Payment exceeds the order's total amount.");
        }

        // Save payment
        Payment savedPayment = paymentRepository.save(payment);

        // Recalculate total paid after saving the new payment
        totalPaid += payment.getAmount();

        // Update order's status if total paid equals total amount
        if (totalPaid >= order.getTotalAmount()) {
            order.setStatus(OrderStatus.ACCEPTED);
        }

        // Update order in repository
        orderRepository.update(order);

        return savedPayment;
    }

    public Payment getPaymentById(Long id) throws RepositoryException {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public void deletePayment(Long id) throws RepositoryException {
        // Before deleting the payment, ensure that it doesn't violate business rules
        Payment payment = getPaymentById(id);

        // Delete the payment
        paymentRepository.deleteById(id);

        // Recalculate total paid for the order
        double totalPaid = getTotalPaidForOrder(payment.getOrderId());

        // Fetch the associated order
        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + payment.getOrderId()));

        // Update order's status based on the new total paid
        if (totalPaid < order.getTotalAmount()) {
            order.setStatus(OrderStatus.PENDING);
        }

        // Update order in repository
        orderRepository.update(order);
    }

    public Payment updatePayment(Payment payment) throws RepositoryException {
        // Get the existing payment
        Payment existingPayment = getPaymentById(payment.getId());

        // Calculate total paid excluding the existing payment amount
        double totalPaidExcludingCurrent = getTotalPaidForOrder(payment.getOrderId()) - existingPayment.getAmount();

        // Check if the updated payment would exceed the order's total amount
        if (totalPaidExcludingCurrent + payment.getAmount() > getOrderTotalAmount(payment.getOrderId())) {
            throw new InvalidPaymentException("Updated payment exceeds the order's total amount.");
        }

        // Update the payment
        Payment updatedPayment = paymentRepository.update(payment);

        // Recalculate total paid after updating the payment
        double totalPaid = totalPaidExcludingCurrent + payment.getAmount();

        // Fetch the associated order
        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + payment.getOrderId()));

        // Update order's status based on the new total paid
        if (totalPaid >= order.getTotalAmount()) {
            order.setStatus(OrderStatus.ACCEPTED);
        } else {
            order.setStatus(OrderStatus.PENDING);
        }

        // Update order in repository
        orderRepository.update(order);

        return updatedPayment;
    }
    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findAll().stream()
                .filter(p -> p.getOrderId().equals(orderId))
                .collect(Collectors.toList());
    }

    private double getTotalPaidForOrder(Long orderId) {
        return getPaymentsByOrderId(orderId).stream()
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    private double getOrderTotalAmount(Long orderId) throws RepositoryException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));
        return order.getTotalAmount();
    }

    // Method to save all payments
    public void saveAll() throws RepositoryException {
        paymentRepository.saveAll();
    }
}