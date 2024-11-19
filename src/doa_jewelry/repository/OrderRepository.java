package doa_jewelry.repository;

import doa_jewelry.entity.Order;
import doa_jewelry.entity.OrderStatus;
import doa_jewelry.exception.EntityAlreadyExistsException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.exception.RepositoryException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository extends MyCrudRepository<Order> {

    private static final String FILE_PATH = "data/orders.csv";
    private final List<Order> orders = new ArrayList<>();

    public OrderRepository() {
        loadFromFile();
    }

    @Override
    public Order save(Order order) throws RepositoryException {
        if (order.getId() == null) {
            Long newId = generateNewId();
            order.setId(newId);
        } else if (existsById(order.getId())) {
            throw new EntityAlreadyExistsException("Order already exists with ID: " + order.getId());
        }

        orders.add(order);
        // Removed saveToFile() call
        return order;
    }

    @Override
    public Order update(Order order) throws RepositoryException {
        Optional<Order> existingOrderOpt = findById(order.getId());
        if (existingOrderOpt.isPresent()) {
            Order existingOrder = existingOrderOpt.get();
            existingOrder.setCustomerId(order.getCustomerId());
            existingOrder.setDate(order.getDate());
            existingOrder.setItems(order.getItems());
            existingOrder.setTotalAmount(order.getTotalAmount());
            existingOrder.setStatus(order.getStatus());
            // Removed saveToFile() call
            return existingOrder;
        } else {
            throw new EntityNotFoundException("Order not found with ID: " + order.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        Order order = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));
        orders.remove(order);
        // Removed saveToFile() call
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    public Long generateNewId() {
        return orders.stream()
                .mapToLong(o -> o.getId() != null ? o.getId() : 0L)
                .max()
                .orElse(0L) + 1;
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) {
                    throw new RuntimeException("Insufficient data in line: " + line);
                }

                Long id = Long.parseLong(data[0].trim());
                Long customerId = Long.parseLong(data[1].trim());
                LocalDate date = LocalDate.parse(data[2].trim());
                OrderStatus status = OrderStatus.valueOf(data[3].trim().toUpperCase());
                double totalAmount = Double.parseDouble(data[4].trim());

                List<Order.Item> items = new ArrayList<>();
                for (int i = 5; i < data.length; i += 2) {
                    if (i + 1 >= data.length) {
                        throw new RuntimeException("Insufficient data for items in line: " + line);
                    }
                    Long jewelryId = Long.parseLong(data[i].trim());
                    int quantity = Integer.parseInt(data[i + 1].trim());
                    items.add(new Order.Item(jewelryId, quantity));
                }

                Order order = new Order(id, customerId, date, items, totalAmount, status);
                orders.add(order);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading orders from file", e);
        }
    }

    private void saveToFile() throws RepositoryException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Order order : orders) {
                StringBuilder sb = new StringBuilder();
                sb.append(order.getId()).append(",")
                        .append(order.getCustomerId()).append(",")
                        .append(order.getDate()).append(",")
                        .append(order.getStatus().name()).append(",")
                        .append(order.getTotalAmount());

                for (Order.Item item : order.getItems()) {
                    sb.append(",").append(item.getJewelryId()).append(",").append(item.getQuantity());
                }

                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Error saving orders to file", e);
        }
    }

    // New method to save all data
    public void saveAll() throws RepositoryException {
        saveToFile();
    }
}
