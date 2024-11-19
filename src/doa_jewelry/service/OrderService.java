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

public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final CustomerRepository customerRepository;
    private final JewelryService jewelryService;

    public OrderService(OrderRepository orderRepository, PaymentService paymentService,
                        CustomerRepository customerRepository, JewelryService jewelryService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.customerRepository = customerRepository;
        this.jewelryService = jewelryService;
    }

    public Order createOrder(Order order) throws RepositoryException {

        if (order.getCustomerId() == null || order.getItems() == null || order.getItems().isEmpty()) {
            throw new RepositoryException("Invalid order data.");
        }

        if (!customerRepository.existsById(order.getCustomerId())) {
            throw new EntityNotFoundException("Customer not found with ID: " + order.getCustomerId());
        }

        if (order.getId() == null) {
            Long newId = orderRepository.generateNewId();
            order.setId(newId);
        } else if (orderRepository.existsById(order.getId())) {
            throw new EntityAlreadyExistsException("Order already exists with ID: " + order.getId());
        }

        double totalAmount = 0.0;
        for (Order.Item item : order.getItems()) {
            Jewelry jewelry = jewelryService.getJewelryById(item.getJewelryId());
            if (jewelry.getStockQuantity() < item.getQuantity()) {
                throw new InsufficientUnitsException("Insufficient stock for jewelry ID: " + item.getJewelryId());
            }
            jewelryService.decreaseStock(item.getJewelryId(), item.getQuantity());
            totalAmount += jewelry.getPrice() * item.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) throws RepositoryException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));
    }

    public List<Order> getAllOrders() throws RepositoryException {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) throws RepositoryException {
        List<Payment> payments = paymentService.getPaymentsByOrderId(id);
        for (Payment payment : payments) {
            paymentService.deletePayment(payment.getId());
        }

        // Restore stock quantities
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));

        for (Order.Item item : order.getItems()) {
            jewelryService.increaseStock(item.getJewelryId(), item.getQuantity());
        }

        orderRepository.deleteById(id);
    }

    public Order updateOrder(Order order) throws RepositoryException {
        Order existingOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + order.getId()));

        if (!existingOrder.getStatus().equals(order.getStatus())) {
            if (order.getStatus() == OrderStatus.CANCELED && existingOrder.getStatus() != OrderStatus.CANCELED) {
                for (Order.Item item : existingOrder.getItems()) {
                    jewelryService.increaseStock(item.getJewelryId(), item.getQuantity());
                }
            } else if (order.getStatus() == OrderStatus.PENDING && existingOrder.getStatus() != OrderStatus.PENDING) {
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

        existingOrder.setCustomerId(order.getCustomerId());
        existingOrder.setDate(order.getDate());
        existingOrder.setItems(order.getItems());
        existingOrder.setTotalAmount(order.getTotalAmount());
        existingOrder.setStatus(order.getStatus());

        return orderRepository.update(existingOrder);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) throws RepositoryException {
        return orderRepository.findAll().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public boolean isJewelryInOrders(Long jewelryId) {
        return orderRepository.findAll().stream()
                .anyMatch(order -> order.getItems().stream()
                        .anyMatch(item -> item.getJewelryId().equals(jewelryId)));
    }

    // Method to save all orders
    public void saveAll() throws RepositoryException {
        orderRepository.saveAll();
    }

}
