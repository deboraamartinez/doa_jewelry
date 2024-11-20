package doa_jewelry.controller;

import doa_jewelry.entity.Jewelry;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.service.JewelryService;

import java.util.List;

public class JewelryController {
    private final JewelryService jewelryService;

    public JewelryController(JewelryService jewelryService) {
        this.jewelryService = jewelryService;
    }

    public Jewelry createJewelry(Jewelry jewelry) {
        try {
            return jewelryService.createJewelry(jewelry);
        } catch (RepositoryException e) {
            System.err.println("Error creating jewelry: " + e.getMessage());
            return null;
        }
    }

    public Jewelry getJewelryById(Long id) {
        try {
            return jewelryService.getJewelryById(id);
        } catch (RepositoryException e) {
            System.err.println("Error retrieving jewelry: " + e.getMessage());
            return null;
        }
    }

    public List<Jewelry> getAllJewelry() {
        try {
            return jewelryService.getAllJewelry();
        } catch (RepositoryException e) {
            System.err.println("Error retrieving jewelry: " + e.getMessage());
            return List.of();
        }
    }

    public void deleteJewelry(Long id) {
        try {
            jewelryService.deleteJewelry(id);
        } catch (RepositoryException e) {
            System.err.println("Error deleting jewelry: " + e.getMessage());
        }
    }

    public Jewelry updateJewelry(Jewelry jewelry) {
        try {
            return jewelryService.updateJewelry(jewelry);
        } catch (RepositoryException e) {
            System.err.println("Error updating jewelry: " + e.getMessage());
            return null;
        }
    }

    public void saveAll() {
        try {
            jewelryService.saveAll();
        } catch (RepositoryException e) {
            System.err.println("Error saving jewelry: " + e.getMessage());
        }
    }
}
