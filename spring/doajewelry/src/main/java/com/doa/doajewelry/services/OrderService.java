package com.doa.doajewelry.services;

import com.doa.doajewelry.dtos.OrderDTO;
import com.doa.doajewelry.entities.Customer;
import com.doa.doajewelry.entities.Employee;
import com.doa.doajewelry.entities.Jewelry;
import com.doa.doajewelry.entities.Order;
import com.doa.doajewelry.entities.enums.OrderStatus;
import com.doa.doajewelry.repositories.CustomerRepository;
import com.doa.doajewelry.repositories.EmployeeRepository;
import com.doa.doajewelry.repositories.JewelryRepository;
import com.doa.doajewelry.repositories.OrderRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service layer for managing Order operations.
 * Handles creation, retrieval, updating, and deletion of orders.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final JewelryRepository jewelryRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * Constructor-based dependency injection for repositories.
     * 
     * @param orderRepository     Repository for Order entities.
     * @param customerRepository  Repository for Customer entities.
     * @param jewelryRepository   Repository for Jewelry entities.
     * @param employeeRepository  Repository for Employee entities.
     */
    public OrderService(OrderRepository orderRepository, 
                        CustomerRepository customerRepository, 
                        JewelryRepository jewelryRepository,
                        EmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.jewelryRepository = jewelryRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Creates a new Order.
     * Validates customer and employee existence, checks for duplicate jewelry items,
     * and ensures jewelry is in stock before creating the order.
     * 
     * @param dto Data Transfer Object containing order details.
     * @return OrderDTO of the created order.
     */
    public OrderDTO createOrder(OrderDTO dto) {
        // Validate if the customer exists
        Customer c = customerRepository.findById(dto.getCustomerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        // Validate if the employee exists
        Employee e = employeeRepository.findById(dto.getEmployeeId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        // Validate duplicates in jewelryIds to prevent multiple entries of the same item
        if (dto.getJewelryIds().size() != dto.getJewelryIds().stream().distinct().count()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate jewelry items in order");
        }

        // Create new Order entity
        Order o = new Order();
        o.setDate(dto.getDate() == null ? LocalDate.now() : dto.getDate()); // Set current date if not provided
        o.setStatus(dto.getStatus() == null ? OrderStatus.PENDING : dto.getStatus()); // Default to PENDING if not provided
        o.setCustomer(c); // Associate the order with the customer
        o.setEmployee(e); // Associate the order with the employee

        // For each jewelry ID, check stock and add to the order
        for (Long jewelryId : dto.getJewelryIds()) {
            Jewelry j = jewelryRepository.findById(jewelryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jewelry not found"));
            if (j.getStockQuantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jewelry out of stock");
            }
            j.setStockQuantity(j.getStockQuantity() - 1); // Decrement stock as the item is being ordered
            jewelryRepository.save(j); // Save the updated jewelry stock
            o.addItem(j); // Add the jewelry item to the order
        }

        // Save the order to the database
        Order saved = orderRepository.save(o);
        
        // Convert the saved Order entity to OrderDTO using the static method
        return OrderDTO.fromEntity(saved);
    }

    /**
     * Retrieves all Orders from the database.
     * 
     * @return List of OrderDTOs representing all orders.
     */
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
            .map(OrderDTO::fromEntity) // Converts each Order entity to DTO using the static method
            .collect(Collectors.toList());
    }

    /**
     * Retrieves a single Order by ID.
     * 
     * @param id The ID of the order to retrieve.
     * @return OrderDTO of the found order.
     */
    public OrderDTO getOrder(Long id) {
        Order o = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return OrderDTO.fromEntity(o); // Converts the Order entity to DTO
    }

    /**
     * Retrieves all Orders by a specific status.
     * 
     * @param status The status of the orders to retrieve (e.g., PENDING, DELIVERED).
     * @return List of OrderDTOs matching the specified status.
     */
    public List<OrderDTO> getAllOrdersByStatus(OrderStatus status) {
        // Fetch all orders with the given status using repository method
        List<Order> orders = orderRepository.findAllByStatus(status);

        // Convert each Order entity to DTO
        return orders.stream()
                     .map(OrderDTO::fromEntity)
                     .collect(Collectors.toList());
    }

    /**
     * Updates an existing Order.
     * Handles changes in jewelry items by adjusting stock accordingly.
     * 
     * @param id  The ID of the order to update.
     * @param dto Data Transfer Object containing updated order details.
     * @return OrderDTO of the updated order.
     */
    public OrderDTO updateOrder(Long id, OrderDTO dto) {
        // Find the existing Order
        Order o = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        // Validate duplicates in the new list to prevent multiple entries of the same item
        if (dto.getJewelryIds().size() != dto.getJewelryIds().stream().distinct().count()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate jewelry items in order");
        }

        // Validate if the new employee exists
        Employee e = employeeRepository.findById(dto.getEmployeeId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        // Return stock for items that are no longer in the updated list
        o.getItems().forEach(j -> {
            if (!dto.getJewelryIds().contains(j.getId())) {
                j.setStockQuantity(j.getStockQuantity() + 1); // Restore stock as the item is removed from the order
                jewelryRepository.save(j); // Save the updated jewelry stock
            }
        });

        // Clear the original items (we will re-add updated ones)
        o.getItems().clear();

        // For each jewelry ID in the updated list, check stock and add to the order
        for (Long jewelryId : dto.getJewelryIds()) {
            Jewelry j = jewelryRepository.findById(jewelryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jewelry not found"));
            if (j.getStockQuantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jewelry out of stock");
            }
            j.setStockQuantity(j.getStockQuantity() - 1); // Decrement stock as the item is being ordered
            jewelryRepository.save(j); // Save the updated jewelry stock
            o.addItem(j); // Add the jewelry item to the order
        }

        // Update other fields (date, status, employee)
        o.setDate(dto.getDate());
        o.setStatus(dto.getStatus());
        o.setEmployee(e);

        // Persist the changes by saving the order
        Order saved = orderRepository.save(o);

        // Convert the updated Order entity to OrderDTO using the static method
        return OrderDTO.fromEntity(saved);
    }

    /**
     * Deletes an Order by ID and restores stock for all associated jewelry items.
     * 
     * @param id The ID of the order to delete.
     */
    public void deleteOrder(Long id) {
        Order o = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        // Restore stock for all items in the order
        o.getItems().forEach(j -> {
            j.setStockQuantity(j.getStockQuantity() + 1); // Increment stock as the order is being deleted
            jewelryRepository.save(j); // Save the updated jewelry stock
        });

        // Delete the order from the database
        orderRepository.delete(o);
    }
}
