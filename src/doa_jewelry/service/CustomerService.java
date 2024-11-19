package doa_jewelry.service;

import doa_jewelry.entity.Customer;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository = new CustomerRepository();

    public Customer createCustomer(Customer customer) throws RepositoryException {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) throws RepositoryException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new doa_jewelry.exception.EntityNotFoundException(
                        "Cliente não encontrado com o ID: " + id));
    }

    public List<Customer> getAllCustomers() throws RepositoryException {
        return customerRepository.findAll();
    }

    public void deleteCustomer(Long id) throws RepositoryException {
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(Customer customer) throws RepositoryException {
        return customerRepository.update(customer);
    }
}
