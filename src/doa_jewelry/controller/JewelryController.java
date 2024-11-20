package doa_jewelry.controller;

import doa_jewelry.entity.Jewelry;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.service.JewelryService;

import java.util.List;

// Controller for managing jewelry-related operations
public class JewelryController {
    private final JewelryService jewelryService;

    // Constructor to initialize the JewelryService dependency
    public JewelryController(JewelryService jewelryService) {
        this.jewelryService = jewelryService;
    }

    // Create a new jewelry item and handle potential exceptions
    public Jewelry createJewelry(Jewelry jewelry) {
        try {
            return jewelryService.createJewelry(jewelry); // Call the service to create the jewelry
        } catch (RepositoryException e) {
            System.err.println("Error creating jewelry: " + e.getMessage());
            return null;
        }
    }

    // Retrieve a jewelry item by its ID and handle potential exceptions
    public Jewelry getJewelryById(Long id) {
        try {
            return jewelryService.getJewelryById(id); // Call the service to get the jewelry by ID
        } catch (RepositoryException e) {
            System.err.println("Error retrieving jewelry: " + e.getMessage());
            return null;
        }
    }

    // Retrieve all jewelry items and handle potential exceptions
    public List<Jewelry> getAllJewelry() {
        try {
            return jewelryService.getAllJewelry(); // Call the service to get all jewelry items
        } catch (RepositoryException e) {
            System.err.println("Error retrieving jewelry: " + e.getMessage());
            return List.of(); // Return an empty list if an error occurs
        }
    }

    // Delete a jewelry item by its ID and handle potential exceptions
    public void deleteJewelry(Long id) {
        try {
            jewelryService.deleteJewelry(id); // Call the service to delete the jewelry
        } catch (RepositoryException e) {
            System.err.println("Error deleting jewelry: " + e.getMessage());
        }
    }

    // Update an existing jewelry item and handle potential exceptions
    public Jewelry updateJewelry(Jewelry jewelry) {
        try {
            return jewelryService.updateJewelry(jewelry); // Call the service to update the jewelry
        } catch (RepositoryException e) {
            System.err.println("Error updating jewelry: " + e.getMessage());
            return null;
        }
    }

    // Save all jewelry items to the storage and handle potential exceptions
    public void saveAll() {
        try {
            jewelryService.saveAll(); // Call the service to save all jewelry items
        } catch (RepositoryException e) {
            System.err.println("Error saving jewelry: " + e.getMessage());
        }
    }
}
