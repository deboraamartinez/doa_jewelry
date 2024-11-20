package doa_jewelry.controller;

import doa_jewelry.dto.OrderWithPayments;
import doa_jewelry.entity.Order;
import doa_jewelry.entity.Payment;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.service.OrderService;
import doa_jewelry.service.PaymentService;

import java.util.List;

// Controller to handle order-related operations, including payments associated with orders
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    // Constructor to initialize dependencies for OrderService and PaymentService
    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    // Create a new order and handle potential exceptions
    public Order createOrder(Order order) {
        try {
            return orderService.createOrder(order); // Call the service to create the order
        } catch (RepositoryException | IllegalArgumentException e) {
            System.err.println("Error creating order: " + e.getMessage());
            return null;
        }
    }

    // Retrieve an order by its ID and handle potential exceptions
    public Order getOrderById(Long id) {
        try {
            return orderService.getOrderById(id); // Call the service to get the order
        } catch (RepositoryException e) {
            System.err.println("Error retrieving order: " + e.getMessage());
            return null;
        }
    }

    // Retrieve an order along with its associated payments by order ID
    public OrderWithPayments getOrderWithPaymentsById(Long id) {
        try {
            Order order = orderService.getOrderById(id); // Retrieve the order by ID
            List<Payment> payments = paymentService.getPaymentsByOrderId(id); // Retrieve associated payments
            return new OrderWithPayments(order, payments); // Return a DTO containing the order and payments
        } catch (RepositoryException e) {
            System.err.println("Error retrieving order with payments: " + e.getMessage());
            return null;
        }
    }

    // Retrieve all orders and handle potential exceptions
    public List<Order> getAllOrders() {
        try {
            return orderService.getAllOrders(); // Call the service to get all orders
        } catch (RepositoryException e) {
            System.err.println("Error retrieving orders: " + e.getMessage());
            return List.of(); // Return an empty list if an error occurs
        }
    }

    // Delete an order by its ID and handle potential exceptions
    public void deleteOrder(Long id) {
        try {
            orderService.deleteOrder(id); // Call the service to delete the order
        } catch (RepositoryException e) {
            System.err.println("Error deleting order: " + e.getMessage());
        }
    }

    // Update an existing order and handle potential exceptions
    public Order updateOrder(Order order) {
        try {
            return orderService.updateOrder(order); // Call the service to update the order
        } catch (RepositoryException | IllegalArgumentException e) {
            System.err.println("Error updating order: " + e.getMessage());
            return null;
        }
    }

    // Save all orders to the storage and handle potential exceptions
    public void saveAll() {
        try {
            orderService.saveAll(); // Call the service to save all orders
        } catch (RepositoryException e) {
            System.err.println("Error saving orders: " + e.getMessage());
        }
    }
}
