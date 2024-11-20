package doa_jewelry.service;

import doa_jewelry.entity.Jewelry;
import doa_jewelry.entity.Order;
import doa_jewelry.entity.OrderStatus;
import doa_jewelry.entity.Payment;
import doa_jewelry.exception.EntityAlreadyExistsException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.exception.InsufficientUnitsException;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.repository.OrderRepository;
import doa_jewelry.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

// Service responsible for handling the business logic related to orders
public class OrderService {

    private final OrderRepository orderRepository; // Repository to manage orders
    private final PaymentService paymentService; // Service to manage payments
    private final CustomerRepository customerRepository; // Repository to manage customers
    private final JewelryService jewelryService; // Service to manage jewelry stock

    // Constructor to initialize the service with required dependencies
    public OrderService(OrderRepository orderRepository, PaymentService paymentService,
                        CustomerRepository customerRepository, JewelryService jewelryService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.customerRepository = customerRepository;
        this.jewelryService = jewelryService;
    }

    // Creates a new order and handles stock validation for jewelry items
    public Order createOrder(Order order) throws RepositoryException {

        // Validate order data
        if (order.getCustomerId() == null || order.getItems() == null || order.getItems().isEmpty()) {
            throw new RepositoryException("Invalid order data.");
        }

        // Check if the customer exists
        if (!customerRepository.existsById(order.getCustomerId())) {
            throw new EntityNotFoundException("Customer not found with ID: " + order.getCustomerId());
        }

        // Assign a new ID if not provided or check for duplicates
        if (order.getId() == null) {
            Long newId = orderRepository.generateNewId();
            order.setId(newId);
        } else if (orderRepository.existsById(order.getId())) {
            throw new EntityAlreadyExistsException("Order already exists with ID: " + order.getId());
        }

        // Calculate the total amount and validate stock
        double totalAmount = 0.0;
        for (Order.Item item : order.getItems()) {
            Jewelry jewelry = jewelryService.getJewelryById(item.getJewelryId());
            if (jewelry.getStockQuantity() < item.getQuantity()) {
                throw new InsufficientUnitsException("Insufficient stock for jewelry ID: " + item.getJewelryId());
            }
            jewelryService.decreaseStock(item.getJewelryId(), item.getQuantity());
            totalAmount += jewelry.getPrice() * item.getQuantity();
        }

        // Set order total amount and initial status
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);

        // Save the order
        return orderRepository.save(order);
    }

    // Retrieves an order by its ID; throws an exception if not found
    public Order getOrderById(Long id) throws RepositoryException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));
    }

    // Retrieves all orders
    public List<Order> getAllOrders() throws RepositoryException {
        return orderRepository.findAll();
    }

    // Deletes an order and restores stock quantities
    public void deleteOrder(Long id) throws RepositoryException {
        // Retrieve all payments for the order and delete them
        List<Payment> payments = paymentService.getPaymentsByOrderId(id);
        for (Payment payment : payments) {
            paymentService.deletePayment(payment.getId());
        }

        // Restore stock for the items in the order
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));

        for (Order.Item item : order.getItems()) {
            jewelryService.increaseStock(item.getJewelryId(), item.getQuantity());
        }

        // Delete the order from the repository
        orderRepository.deleteById(id);
    }

    // Updates an existing order and adjusts stock if necessary
    public Order updateOrder(Order order) throws RepositoryException {
        // Find the existing order
        Order existingOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + order.getId()));

        // Handle stock adjustments if the status changes
        if (!existingOrder.getStatus().equals(order.getStatus())) {
            if (order.getStatus() == OrderStatus.CANCELED && existingOrder.getStatus() != OrderStatus.CANCELED) {
                // Restore stock for canceled orders
                for (Order.Item item : existingOrder.getItems()) {
                    jewelryService.increaseStock(item.getJewelryId(), item.getQuantity());
                }
            } else if (order.getStatus() == OrderStatus.PENDING && existingOrder.getStatus() != OrderStatus.PENDING) {
                // Deduct stock for orders reverting to pending
                for (Order.Item item : existingOrder.getItems()) {
                    Jewelry jewelry = jewelryService.getJewelryById(item.getJewelryId());
                    if (jewelry.getStockQuantity() < item.getQuantity()) {
                        throw new InsufficientUnitsException(
                                "Insufficient stock for jewelry ID: " + item.getJewelryId());
                    }
                    jewelryService.decreaseStock(item.getJewelryId(), item.getQuantity());
                }
            }
        }

        // Update order details
        existingOrder.setCustomerId(order.getCustomerId());
        existingOrder.setDate(order.getDate());
        existingOrder.setItems(order.getItems());
        existingOrder.setTotalAmount(order.getTotalAmount());
        existingOrder.setStatus(order.getStatus());

        // Save updated order
        return orderRepository.update(existingOrder);
    }

    // Retrieves all orders associated with a specific customer
    public List<Order> getOrdersByCustomerId(Long customerId) throws RepositoryException {
        return orderRepository.findAll().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    // Checks if a specific jewelry item is part of any orders
    public boolean isJewelryInOrders(Long jewelryId) {
        return orderRepository.findAll().stream()
                .anyMatch(order -> order.getItems().stream()
                        .anyMatch(item -> item.getJewelryId().equals(jewelryId)));
    }

    // Saves all orders to persistent storage
    public void saveAll() throws RepositoryException {
        orderRepository.saveAll();
    }

}
