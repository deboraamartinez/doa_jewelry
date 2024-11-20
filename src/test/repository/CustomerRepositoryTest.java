package test.repository;

import doa_jewelry.entity.Customer;
import doa_jewelry.repository.CustomerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerRepositoryTest {

    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        customerRepository = new CustomerRepository();
        customerRepository.deleteAll(); 
    }

    @After
    public void tearDown() {
        customerRepository.deleteAll(); 
    }

    @Test
    public void testSaveCustomer() {
        Customer customer = new Customer("John Doe", "123456789", "john@example.com", "555-1234", "123 Main St");
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer.getId());
        assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    public void testFindById() {
        Customer customer = new Customer("John Doe", "123456789", "john@example.com", "555-1234", "123 Main St");
        Customer savedCustomer = customerRepository.save(customer);

        Customer retrievedCustomer = customerRepository.findById(savedCustomer.getId()).orElse(null);

        assertNotNull(retrievedCustomer);
        assertEquals(savedCustomer.getId(), retrievedCustomer.getId());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer("John Doe", "123456789", "john@example.com", "555-1234", "123 Main St");
        Customer savedCustomer = customerRepository.save(customer);

        customerRepository.deleteById(savedCustomer.getId());

        assertTrue(customerRepository.findAll().isEmpty());
    }
}
