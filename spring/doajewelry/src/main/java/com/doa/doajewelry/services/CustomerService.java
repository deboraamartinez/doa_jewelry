package com.doa.doajewelry.services;

import com.doa.doajewelry.dtos.CustomerDTO;
import com.doa.doajewelry.entities.Customer;
import com.doa.doajewelry.entities.embedded.Address;
import com.doa.doajewelry.exceptions.DuplicateEmailException;
import com.doa.doajewelry.exceptions.InvalidNIFException;
import com.doa.doajewelry.repositories.CustomerRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service layer for managing Customer operations.
 * Handles creation, retrieval, updating, and deletion of customers.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository; // Injects the CustomerRepository for database interactions

    /**
     * Creates a new customer.
     * Validates the uniqueness of NIF and email before saving.
     * 
     * @param dto Data Transfer Object containing customer details.
     * @return CustomerDTO of the created customer.
     */
    public CustomerDTO createCustomer(CustomerDTO dto) {
        validateNIFUniqueness(dto.getNif()); // Ensures NIF is unique
        validateEmailUniqueness(dto.getEmail()); // Ensures email is unique if provided
        Customer customer = convertToEntity(dto); // Converts DTO to Customer entity
        Customer savedCustomer = customerRepository.save(customer); // Saves the customer to the database
        return convertToDTO(savedCustomer); // Converts the saved entity back to DTO
    }

    /**
     * Retrieves all customers from the database.
     * 
     * @return List of CustomerDTOs representing all customers.
     */
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertToDTO) // Converts each Customer entity to DTO
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single customer by ID.
     * 
     * @param id The ID of the customer to retrieve.
     * @return CustomerDTO of the found customer.
     */
    public CustomerDTO getCustomer(Long id) {
        Customer customer = findCustomerById(id); // Fetches the customer or throws an exception if not found
        return convertToDTO(customer); // Converts the entity to DTO
    }

    /**
     * Updates an existing customer.
     * Validates uniqueness if NIF or email are being changed.
     * 
     * @param id  The ID of the customer to update.
     * @param dto Data Transfer Object containing updated customer details.
     * @return CustomerDTO of the updated customer.
     */
    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer existingCustomer = findCustomerById(id); // Retrieves the existing customer

        // If NIF is being changed, validate its uniqueness
        if (!existingCustomer.getNif().equals(dto.getNif())) {
            validateNIFUniqueness(dto.getNif());
        }

        // If email is being changed, validate its uniqueness
        if (!existingCustomer.getEmail().equals(dto.getEmail())) {
            validateEmailUniqueness(dto.getEmail());
        }

        updateEntityFromDTO(existingCustomer, dto); // Updates the existing entity with DTO values
        Customer updatedCustomer = customerRepository.save(existingCustomer); // Saves the updated customer
        return convertToDTO(updatedCustomer); // Converts the updated entity to DTO
    }

    /**
     * Deletes a customer by ID.
     * 
     * @param id The ID of the customer to delete.
     */
    public void deleteCustomer(Long id) {
        Customer customer = findCustomerById(id); // Retrieves the customer or throws an exception if not found
        customerRepository.delete(customer); // Deletes the customer from the database
    }

    // Utility methods

    /**
     * Validates that the provided NIF is unique across all customers.
     * 
     * @param nif The NIF to validate.
     * @throws InvalidNIFException if the NIF already exists.
     */
    private void validateNIFUniqueness(String nif) {
        if (customerRepository.existsByNif(nif)) {
            throw new InvalidNIFException("NIF already exists");
        }
    }

    /**
     * Validates that the provided email is unique across all customers.
     * 
     * @param email The email to validate.
     * @throws DuplicateEmailException if the email already exists.
     */
    private void validateEmailUniqueness(String email) {
        if (email != null && customerRepository.findAll().stream().anyMatch(c -> email.equalsIgnoreCase(c.getEmail()))) {
            throw new DuplicateEmailException("Email already exists: " + email);
        }
    }

    /**
     * Finds a customer by ID.
     * 
     * @param id The ID of the customer to find.
     * @return The Customer entity.
     * @throws ResponseStatusException with NOT_FOUND status if customer does not exist.
     */
    private Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    /**
     * Converts a Customer entity to a CustomerDTO.
     * 
     * @param customer The Customer entity to convert.
     * @return The corresponding CustomerDTO.
     */
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setNif(customer.getNif());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());

        if (customer.getAddress() != null) {
            dto.setStreet(customer.getAddress().getStreet());
            dto.setCity(customer.getAddress().getCity());
            dto.setState(customer.getAddress().getState());
            dto.setZipCode(customer.getAddress().getZipCode());
            dto.setCountry(customer.getAddress().getCountry());
        }

        return dto;
    }

    /**
     * Converts a CustomerDTO to a Customer entity.
     * 
     * @param dto The CustomerDTO to convert.
     * @return The corresponding Customer entity.
     */
    private Customer convertToEntity(CustomerDTO dto) {
        Address address = new Address(
                dto.getStreet(),
                dto.getCity(),
                dto.getState(),
                dto.getZipCode(),
                dto.getCountry()
        );

        return new Customer(
                dto.getNif(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                address
        );
    }

    /**
     * Updates an existing Customer entity with values from a CustomerDTO.
     * 
     * @param customer The existing Customer entity to update.
     * @param dto      The CustomerDTO containing updated values.
     */
    private void updateEntityFromDTO(Customer customer, CustomerDTO dto) {
        customer.setNif(dto.getNif());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());

        Address address = customer.getAddress();
        if (address == null) {
            address = new Address();
            customer.setAddress(address);
        }

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
    }
}
