package doa_jewelry.repository;

import doa_jewelry.entity.Customer;
import doa_jewelry.exception.EntityAlreadyExistsException;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.exception.EntityNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository extends MyCrudRepository<Customer> {

    private static final String FILE_PATH = "data/customer.csv";
    private final List<Customer> customers = new ArrayList<>();

    public CustomerRepository() {
        loadFromFile();
    }

    @Override
    public Customer save(Customer customer) throws RepositoryException {
        // Verifica se já existe um cliente com o mesmo NIF
        boolean nifExists = customers.stream()
                .anyMatch(c -> c.getNif().equalsIgnoreCase(customer.getNif()));
        if (nifExists) {
            throw new EntityAlreadyExistsException("Já existe um cliente com o NIF " + customer.getNif());
        }

        boolean emailExists = customers.stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(customer.getEmail()));
        if (emailExists) {
            throw new EntityAlreadyExistsException("Já existe um cliente com o email " + customer.getEmail());
        }

        if (customer.getId() == null) {

            Long newId = customers.stream()
                    .mapToLong(c -> c.getId() != null ? c.getId() : 0L)
                    .max()
                    .orElse(0L) + 1;
            customer.setId(newId);
        }

        customers.add(customer);
        saveToFile();
        return customer;
    }

    @Override
    public Customer update(Customer customer) throws RepositoryException {
        Optional<Customer> existingCustomerOpt = findById(customer.getId());
        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();
            existingCustomer.setName(customer.getName());
            existingCustomer.setNif(customer.getNif());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            existingCustomer.setAddress(customer.getAddress());
            saveToFile();
            return existingCustomer;
        } else {
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + customer.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        boolean removed = customers.removeIf(c -> c.getId().equals(id));
        if (!removed) {
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + id);
        }
        saveToFile();
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) {
                    throw new RepositoryException("Formato de dados inválido em customer.csv");
                }

                Long id = Long.parseLong(data[0]);

                Customer customer = new Customer(
                        data[1], // name
                        data[2], // nif
                        data[3], // email
                        data[4], // phoneNumber
                        data[5] // address
                );

                customer.setId(id);
                customers.add(customer);
            }
        } catch (IOException | RepositoryException e) {
            throw new RuntimeException("Erro ao carregar clientes do arquivo", e);
        }
    }

    private void saveToFile() throws RepositoryException {
        try {
            File file = new File(FILE_PATH);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean dirCreated = parentDir.mkdirs();
                if (!dirCreated) {
                    throw new RepositoryException("Não foi possível criar o diretório para salvar os clientes.");
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Customer customer : customers) {
                    bw.write(customer.getId() + "," + customer.getName() + "," + customer.getNif() + ","
                            + customer.getEmail() + "," + customer.getPhoneNumber() + "," + customer.getAddress());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new RepositoryException("Erro ao salvar clientes no arquivo", e);
        }
    }
}
