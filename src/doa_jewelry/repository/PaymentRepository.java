package doa_jewelry.repository;

import doa_jewelry.entity.Payment;
import doa_jewelry.entity.PaymentMethod;
import doa_jewelry.exception.EntityAlreadyExistsException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.exception.RepositoryException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Repository for managing payment data, including persistence to a CSV file
public class PaymentRepository extends MyCrudRepository<Payment> {

    private static final String FILE_PATH = "data/payments.csv"; // Path to the CSV file for payment data
    private final List<Payment> payments = new ArrayList<>(); // In-memory list of payments

    // Constructor that loads payments from the CSV file into memory
    public PaymentRepository() {
        loadFromFile();
    }

    @Override
    public Payment save(Payment payment) throws RepositoryException {
        // Assign a new ID if the payment doesn't have one
        if (payment.getId() == null) {
            payment.setId(generateNewId());
        } else if (existsById(payment.getId())) {
            // Check if a payment with the same ID already exists
            throw new EntityAlreadyExistsException("Payment already exists with ID: " + payment.getId());
        }

        // Add the payment to the in-memory list
        payments.add(payment);
        return payment;
    }

    @Override
    public Payment update(Payment payment) throws RepositoryException {
        // Find the existing payment by ID
        Optional<Payment> existingPaymentOpt = findById(payment.getId());
        if (existingPaymentOpt.isPresent()) {
            Payment existingPayment = existingPaymentOpt.get();

            // Update the payment properties
            existingPayment.setAmount(payment.getAmount());
            existingPayment.setDate(payment.getDate());
            existingPayment.setMethod(payment.getMethod());
            existingPayment.setOrderId(payment.getOrderId());

            return existingPayment;
        } else {
            throw new EntityNotFoundException("Payment not found with ID: " + payment.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        // Find the payment by ID and remove it
        Payment payment = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        payments.remove(payment);
    }

    @Override
    public List<Payment> findAll() {
        // Return a copy of the in-memory list to prevent external modifications
        return new ArrayList<>(payments);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        // Find a payment by its ID
        return payments.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    // Generate a new unique ID for a payment
    private Long generateNewId() {
        return payments.stream()
                .mapToLong(p -> p.getId() != null ? p.getId() : 0L)
                .max()
                .orElse(0L) + 1;
    }

    // Load payments from the CSV file into the in-memory list
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 5) {
                    throw new RuntimeException("Insufficient data in line: " + line);
                }

                // Parse payment data from the CSV line
                Long id = Long.parseLong(data[0].trim());
                double amount = Double.parseDouble(data[1].trim());
                LocalDate date = LocalDate.parse(data[2].trim());
                PaymentMethod method = PaymentMethod.valueOf(data[3].trim().toUpperCase());
                Long orderId = Long.parseLong(data[4].trim());

                // Create a Payment object and add it to the list
                Payment payment = new Payment(amount, date, method, orderId);
                payment.setId(id);
                payments.add(payment);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading payments from file", e);
        }
    }

    // Save all payments from the in-memory list to the CSV file
    private void saveToFile() throws RepositoryException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Payment payment : payments) {
                StringBuilder sb = new StringBuilder();
                sb.append(payment.getId()).append(",")
                        .append(payment.getAmount()).append(",")
                        .append(payment.getDate()).append(",")
                        .append(payment.getMethod().name()).append(",")
                        .append(payment.getOrderId());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Error saving payments to file", e);
        }
    }

    // Save all payments to the CSV file
    public void saveAll() throws RepositoryException {
        saveToFile();
    }

    // Delete all payments and clear the CSV file
    public void deleteAll() {
        payments.clear();
        saveToFile();
    }
}
