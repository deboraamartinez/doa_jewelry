package doa_jewelry.service;

import doa_jewelry.entity.Jewelry;
import doa_jewelry.entity.Order;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.exception.InsufficientUnitsException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.repository.JewelryRepository;
import doa_jewelry.repository.OrderRepository;

import java.util.List;

public class JewelryService {
    private final JewelryRepository jewelryRepository;
    private final OrderRepository orderRepository;

    public JewelryService(JewelryRepository jewelryRepository, OrderRepository orderRepository) {
        this.jewelryRepository = jewelryRepository;
        this.orderRepository = orderRepository;
    }

    public Jewelry createJewelry(Jewelry jewelry) throws RepositoryException {
        return jewelryRepository.save(jewelry);
    }

    public Jewelry getJewelryById(Long id) throws RepositoryException {
        return jewelryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jewelry not found for id: " + id));
    }

    public List<Jewelry> getAllJewelry() throws RepositoryException {
        return jewelryRepository.findAll();
    }

    public void deleteJewelry(Long id) throws RepositoryException {
        // Check if jewelry is part of any orders
        boolean isInOrders = orderRepository.findAll().stream()
                .anyMatch(order -> order.getItems().stream()
                        .anyMatch(item -> item.getJewelryId().equals(id)));
        if (isInOrders) {
            throw new RepositoryException("Cannot delete jewelry item that is part of existing orders.");
        }
        jewelryRepository.deleteById(id);
    }

    public Jewelry updateJewelry(Jewelry jewelry) throws RepositoryException {
        return jewelryRepository.update(jewelry);
    }

    public void decreaseStock(Long jewelryId, int quantity) throws RepositoryException {
        Jewelry jewelry = getJewelryById(jewelryId);
        if (jewelry.getStockQuantity() < quantity) {
            throw new InsufficientUnitsException("Insufficient stock for jewelry ID: " + jewelryId);
        }
        jewelry.setStockQuantity(jewelry.getStockQuantity() - quantity);
        jewelryRepository.update(jewelry);
    }

    public void increaseStock(Long jewelryId, int quantity) throws RepositoryException {
        Jewelry jewelry = getJewelryById(jewelryId);
        jewelry.setStockQuantity(jewelry.getStockQuantity() + quantity);
        jewelryRepository.update(jewelry);
    }

    // Method to save all jewelry
    public void saveAll() throws RepositoryException {
        jewelryRepository.saveAll();
    }
}
