package doa_jewelry.controller;

import doa_jewelry.entity.Customer;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.service.CustomerService;

import java.util.List;

// Controller for managing customer-related operations
public class CustomerController {
    private final CustomerService customerService;

    // Constructor to initialize the CustomerService dependency
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create a new customer and handle potential exceptions
    public Customer createCustomer(Customer customer) {
        try {
            return customerService.createCustomer(customer); // Call the service to create the customer
        } catch (RepositoryException e) {
            System.err.println("Error creating customer: " + e.getMessage());
            return null;
        }
    }

    // Retrieve a customer by their ID and handle potential exceptions
    public Customer getCustomerById(Long id) {
        try {
            return customerService.getCustomerById(id); // Call the service to get the customer by ID
        } catch (RepositoryException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
            return null;
        }
    }

    // Retrieve all customers and handle potential exceptions
    public List<Customer> getAllCustomers() {
        try {
            return customerService.getAllCustomers(); // Call the service to get all customers
        } catch (RepositoryException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
            return List.of(); // Return an empty list if an error occurs
        }
    }

    // Delete a customer by their ID and handle potential exceptions
    public void deleteCustomer(Long id) {
        try {
            customerService.deleteCustomer(id); // Call the service to delete the customer
        } catch (RepositoryException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
        }
    }

    // Update an existing customer and handle potential exceptions
    public Customer updateCustomer(Customer customer) {
        try {
            return customerService.updateCustomer(customer); // Call the service to update the customer
        } catch (RepositoryException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return null;
        }
    }

    // Save all customers to the storage and handle potential exceptions
    public void saveAll() {
        try {
            customerService.saveAll(); // Call the service to save all customers
        } catch (RepositoryException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }
}
