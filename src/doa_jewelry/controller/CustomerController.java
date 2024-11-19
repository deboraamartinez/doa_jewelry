package doa_jewelry.controller;

import doa_jewelry.entity.Customer;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.service.CustomerService;

import java.util.List;

public class CustomerController {
    private final CustomerService customerService = new CustomerService();

    public Customer createCustomer(Customer customer) {
        try {
            return customerService.createCustomer(customer);
        } catch (RepositoryException e) {
            System.err.println("Error creating customer: " + e.getMessage());
            return null;
        }
    }

    public Customer getCustomerById(Long id) {
        try {
            return customerService.getCustomerById(id);
        } catch (RepositoryException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
            return null;
        }
    }

    public List<Customer> getAllCustomers() {
        try {
            return customerService.getAllCustomers();
        } catch (RepositoryException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
            return List.of();
        }
    }

    public void deleteCustomer(Long id) {
        try {
            customerService.deleteCustomer(id);
        } catch (RepositoryException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
        }
    }

    public Customer updateCustomer(Customer customer) {
        try {
            return customerService.updateCustomer(customer);
        } catch (RepositoryException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return null;
        }
    }
}
