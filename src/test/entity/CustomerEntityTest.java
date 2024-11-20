package test.entity;

import doa_jewelry.entity.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerEntityTest {

    @Test
    public void testCustomerInitialization() {
        Customer customer = new Customer("Charlie Brown", "123415", "charlie@example.com", "1234567890", "123 Main St");

        assertEquals("Charlie Brown", customer.getName());
        assertEquals("123415", customer.getNif());
        assertEquals("charlie@example.com", customer.getEmail());
        assertEquals("1234567890", customer.getPhoneNumber());
        assertEquals("123 Main St", customer.getAddress());
    }
}
