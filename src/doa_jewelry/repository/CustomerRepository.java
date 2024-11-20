package doa_jewelry.repository;

import doa_jewelry.entity.Customer;
import doa_jewelry.exception.EntityAlreadyExistsException;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.exception.EntityNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Repository to manage customer data, including persistence to a CSV file
public class CustomerRepository extends MyCrudRepository<Customer> {

    private static final String FILE_PATH = "data/customer.csv"; // Path to the CSV file
    private final List<Customer> customers = new ArrayList<>(); // In-memory list of customers

    // Constructor loads data from the CSV file into the in-memory list
    public CustomerRepository() {
        loadFromFile();
    }

    @Override
    public Customer save(Customer customer) throws RepositoryException {
        // Check if a customer with the same NIF already exists
        boolean nifExists = customers.stream()
                .anyMatch(c -> c.getNif().equalsIgnoreCase(customer.getNif()));
        if (nifExists) {
            throw new EntityAlreadyExistsException("Customer with NIF " + customer.getNif() + " already exists.");
        }

        // Check if a customer with the same email already exists
        boolean emailExists = customers.stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(customer.getEmail()));
        if (emailExists) {
            throw new EntityAlreadyExistsException("Customer with email " + customer.getEmail() + " already exists.");
        }

        // Assign a new ID if none is provided
        if (customer.getId() == null) {
            Long newId = customers.stream()
                    .mapToLong(c -> c.getId() != null ? c.getId() : 0L)
                    .max()
                    .orElse(0L) + 1;
            customer.setId(newId);
        }

        // Add the customer to the in-memory list
        customers.add(customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) throws RepositoryException {
        // Find the existing customer by ID
        Optional<Customer> existingCustomerOpt = findById(customer.getId());
        if (existingCustomerOpt.isPresent()) {
            // Update the customer details
            Customer existingCustomer = existingCustomerOpt.get();
            existingCustomer.setName(customer.getName());
            existingCustomer.setNif(customer.getNif());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            existingCustomer.setAddress(customer.getAddress());
            return existingCustomer;
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + customer.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        // Remove the customer with the given ID from the list
        boolean removed = customers.removeIf(c -> c.getId().equals(id));
        if (!removed) {
            throw new EntityNotFoundException("Customer not found for ID: " + id);
        }
    }

    @Override
    public List<Customer> findAll() {
        // Return a copy of the in-memory list to prevent external modification
        return new ArrayList<>(customers);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        // Find a customer by their ID
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public boolean existsById(Long id) {
        // Check if a customer exists with the given ID
        return customers.stream().anyMatch(c -> c.getId().equals(id));
    }

    // Load customers from the CSV file into the in-memory list
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) {
                    throw new RepositoryException("Invalid format for customer");
                }

                // Parse customer data from the CSV line
                Long id = Long.parseLong(data[0]);

                Customer customer = new Customer(
                        data[1], // name
                        data[2], // nif
                        data[3], // email
                        data[4], // phoneNumber
                        data[5]  // address
                );

                customer.setId(id);
                customers.add(customer);
            }
        } catch (IOException | RepositoryException e) {
            throw new RuntimeException("Error loading customers from CSV", e);
        }
    }

    // Save the in-memory list of customers to the CSV file
    private void saveToFile() throws RepositoryException {
        try {
            File file = new File(FILE_PATH);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean dirCreated = parentDir.mkdirs();
                if (!dirCreated) {
                    throw new RepositoryException("Error creating directory for CSV file.");
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Customer customer : customers) {
                    // Write each customer's data to the CSV file
                    bw.write(customer.getId() + "," + customer.getName() + "," + customer.getNif() + ","
                            + customer.getEmail() + "," + customer.getPhoneNumber() + "," + customer.getAddress());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new RepositoryException("Error saving customers to CSV", e);
        }
    }

    // Save all customers to the CSV file
    public void saveAll() throws RepositoryException {
        saveToFile();
    }

    // Delete all customers and clear the CSV file
    public void deleteAll() {
        customers.clear();
        saveToFile();
    }
}
