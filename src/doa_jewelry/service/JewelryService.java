package doa_jewelry.service;

import doa_jewelry.entity.Jewelry;
import doa_jewelry.entity.Order;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.exception.InsufficientUnitsException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.repository.JewelryRepository;
import doa_jewelry.repository.OrderRepository;

import java.util.List;

// Service responsible for handling business logic related to jewelry items
public class JewelryService {
    private final JewelryRepository jewelryRepository; // Repository to manage jewelry data
    private final OrderRepository orderRepository; // Repository to access order data

    // Constructor to initialize the service with the necessary repositories
    public JewelryService(JewelryRepository jewelryRepository, OrderRepository orderRepository) {
        this.jewelryRepository = jewelryRepository;
        this.orderRepository = orderRepository;
    }

    // Creates a new jewelry item and saves it to the repository
    public Jewelry createJewelry(Jewelry jewelry) throws RepositoryException {
        return jewelryRepository.save(jewelry);
    }

    // Retrieves a jewelry item by its ID; throws an exception if not found
    public Jewelry getJewelryById(Long id) throws RepositoryException {
        return jewelryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jewelry not found for id: " + id));
    }

    // Retrieves a list of all jewelry items
    public List<Jewelry> getAllJewelry() throws RepositoryException {
        return jewelryRepository.findAll();
    }

    // Deletes a jewelry item if it is not part of any existing orders
    public void deleteJewelry(Long id) throws RepositoryException {
        // Check if the jewelry item is part of any orders
        boolean isInOrders = orderRepository.findAll().stream()
                .anyMatch(order -> order.getItems().stream()
                        .anyMatch(item -> item.getJewelryId().equals(id)));
        if (isInOrders) {
            throw new RepositoryException("Cannot delete jewelry item that is part of existing orders.");
        }
        // Delete the jewelry item if not part of any orders
        jewelryRepository.deleteById(id);
    }

    // Updates the details of an existing jewelry item
    public Jewelry updateJewelry(Jewelry jewelry) throws RepositoryException {
        return jewelryRepository.update(jewelry);
    }

    // Decreases the stock of a jewelry item; throws an exception if there is insufficient stock
    public void decreaseStock(Long jewelryId, int quantity) throws RepositoryException {
        Jewelry jewelry = getJewelryById(jewelryId);
        if (jewelry.getStockQuantity() < quantity) {
            throw new InsufficientUnitsException("Insufficient stock for jewelry ID: " + jewelryId);
        }
        jewelry.setStockQuantity(jewelry.getStockQuantity() - quantity);
        jewelryRepository.update(jewelry);
    }

    // Increases the stock of a jewelry item
    public void increaseStock(Long jewelryId, int quantity) throws RepositoryException {
        Jewelry jewelry = getJewelryById(jewelryId);
        jewelry.setStockQuantity(jewelry.getStockQuantity() + quantity);
        jewelryRepository.update(jewelry);
    }

    // Saves all jewelry data to persistent storage
    public void saveAll() throws RepositoryException {
        jewelryRepository.saveAll();
    }
}
