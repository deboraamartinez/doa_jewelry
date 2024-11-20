package doa_jewelry.service;

import doa_jewelry.entity.Customer;
import doa_jewelry.entity.Order;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderService orderService;

    public CustomerService(CustomerRepository customerRepository, OrderService orderService) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }

    public Customer createCustomer(Customer customer) throws RepositoryException {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) throws RepositoryException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found for id: " + id));
    }

    public List<Customer> getAllCustomers() throws RepositoryException {
        return customerRepository.findAll();
    }

    public void deleteCustomer(Long id) throws RepositoryException {
        List<Order> orders = orderService.getOrdersByCustomerId(id);
        for (Order order : orders) {
            orderService.deleteOrder(order.getId());
        }
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(Customer customer) throws RepositoryException {
        return customerRepository.update(customer);
    }

    public void saveAll() throws RepositoryException {
        customerRepository.saveAll();
    }
}
