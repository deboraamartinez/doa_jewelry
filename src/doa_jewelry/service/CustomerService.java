package doa_jewelry.service;

import doa_jewelry.entity.Customer;
import doa_jewelry.entity.Order;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.repository.CustomerRepository;

import java.util.List;

// Service responsible for managing customers and their related business logic
public class CustomerService {

    private final CustomerRepository customerRepository; // Repository to manage customer data
    private final OrderService orderService; // Service to handle orders related to customers

    // Constructor that initializes the service with required dependencies
    public CustomerService(CustomerRepository customerRepository, OrderService orderService) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }

    // Creates a new customer and saves it in the repository
    public Customer createCustomer(Customer customer) throws RepositoryException {
        return customerRepository.save(customer);
    }

    // Retrieves a customer by ID; throws an exception if not found
    public Customer getCustomerById(Long id) throws RepositoryException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found for id: " + id));
    }

    // Fetches all customers from the repository
    public List<Customer> getAllCustomers() throws RepositoryException {
        return customerRepository.findAll();
    }

    // Deletes a customer and all associated orders from the repository
    public void deleteCustomer(Long id) throws RepositoryException {
        // Get all orders associated with the customer
        List<Order> orders = orderService.getOrdersByCustomerId(id);

        // Delete all associated orders
        for (Order order : orders) {
            orderService.deleteOrder(order.getId());
        }

        // Remove the customer from the repository
        customerRepository.deleteById(id);
    }

    // Updates an existing customer's details in the repository
    public Customer updateCustomer(Customer customer) throws RepositoryException {
        return customerRepository.update(customer);
    }

    // Saves all customer data to persistent storage
    public void saveAll() throws RepositoryException {
        customerRepository.saveAll();
    }
}
