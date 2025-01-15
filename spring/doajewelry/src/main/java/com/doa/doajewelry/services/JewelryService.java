package com.doa.doajewelry.services;

import com.doa.doajewelry.dtos.JewelryDTO;
import com.doa.doajewelry.entities.Earring;
import com.doa.doajewelry.entities.Jewelry;
import com.doa.doajewelry.entities.Necklace;
import com.doa.doajewelry.entities.Ring;
import com.doa.doajewelry.entities.enums.JewelryType;
import com.doa.doajewelry.repositories.JewelryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service layer for managing Jewelry operations.
 * Handles creation, retrieval, updating, and deletion of jewelry items.
 * Supports different types of jewelry such as Necklaces, Earrings, and Rings.
 */
@Service
public class JewelryService {

    private final JewelryRepository jewelryRepository;

    /**
     * Constructor-based dependency injection for the JewelryRepository.
     * 
     * @param jewelryRepository Repository for Jewelry entities.
     */
    public JewelryService(JewelryRepository jewelryRepository) {
        this.jewelryRepository = jewelryRepository;
    }

    /**
     * Creates a new Jewelry item based on its type.
     * Validates required fields specific to each jewelry type.
     * 
     * @param dto Data Transfer Object containing jewelry details.
     * @return JewelryDTO of the created jewelry item.
     */
    @Transactional
    public JewelryDTO createJewelry(JewelryDTO dto) {
        // Validate that jewelryType and category are provided
        if (dto.getJewelryType() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jewelry type required");
        if (dto.getCategory() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category required");

        Jewelry j;
        // Determine the type of jewelry to create based on jewelryType field
        switch (dto.getJewelryType()) {
            case NECKLACE:
                // Validate that length is provided for Necklaces
                if (dto.getLength() == null)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Length is required for Necklace");
                Necklace n = new Necklace();
                n.setName(dto.getName());
                n.setMaterial(dto.getMaterial());
                n.setWeight(dto.getWeight());
                n.setPrice(dto.getPrice());
                n.setStockQuantity(dto.getStockQuantity());
                n.setCategory(dto.getCategory());
                n.setLength(dto.getLength());
                j = n;
                break;

            case EARRING:
                // Validate that claspType is provided for Earrings
                if (dto.getClaspType() == null || dto.getClaspType().isEmpty())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clasp Type is required for Earring");
                Earring e = new Earring();
                e.setName(dto.getName());
                e.setMaterial(dto.getMaterial());
                e.setWeight(dto.getWeight());
                e.setPrice(dto.getPrice());
                e.setStockQuantity(dto.getStockQuantity());
                e.setCategory(dto.getCategory());
                e.setClaspType(dto.getClaspType());
                j = e;
                break;

            case RING:
                // Validate that size is provided for Rings
                if (dto.getSize() == null || dto.getSize().isEmpty())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size is required for Ring");
                Ring r = new Ring();
                r.setName(dto.getName());
                r.setMaterial(dto.getMaterial());
                r.setWeight(dto.getWeight());
                r.setPrice(dto.getPrice());
                r.setStockQuantity(dto.getStockQuantity());
                r.setCategory(dto.getCategory());
                r.setSize(dto.getSize());
                j = r;
                break;

            default:
                // If jewelryType is invalid, throw an exception
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported jewelry type");
        }

        // Save the Jewelry entity to the database
        Jewelry saved = jewelryRepository.save(j);
        dto.setId(saved.getId()); // Populate the DTO with the generated ID
        return dto;
    }

    /**
     * Retrieves all Jewelry items from the database.
     * 
     * @return List of JewelryDTOs representing all jewelry items.
     */
    public List<JewelryDTO> getAllJewelry() {
        return jewelryRepository.findAll()
            .stream()
            .map(JewelryDTO::fromEntity) // Converts each Jewelry entity to DTO using the static method
            .collect(Collectors.toList());
    }

    /**
     * Retrieves a single Jewelry item by ID.
     * 
     * @param id The ID of the jewelry item to retrieve.
     * @return JewelryDTO of the found jewelry item.
     */
    public JewelryDTO getJewelry(Long id) {
        Jewelry j = jewelryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jewelry not found"));
        
        // Convert the Jewelry entity to DTO using the static method
        return JewelryDTO.fromEntity(j);
    }

    /**
     * Retrieves all Jewelry items of a specific type.
     * 
     * @param type The type of jewelry to retrieve (NECKLACE, EARRING, RING).
     * @return List of JewelryDTOs matching the specified type.
     */
    public List<JewelryDTO> getAllByType(JewelryType type) {
        if (type == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jewelry type must be specified");
        }

        List<? extends Jewelry> jewelryList;

        // Fetch jewelry based on the specified type using repository methods
        switch (type) {
            case NECKLACE:
                jewelryList = jewelryRepository.findAllByType(Necklace.class);
                break;
            case EARRING:
                jewelryList = jewelryRepository.findAllByType(Earring.class);
                break;
            case RING:
                jewelryList = jewelryRepository.findAllByType(Ring.class);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported jewelry type");
        }

        // Convert each Jewelry entity to DTO
        return jewelryList.stream()
            .map(JewelryDTO::fromEntity)
            .collect(Collectors.toList());
    }

    /**
     * Updates an existing Jewelry item.
     * Handles changes in jewelry type by migrating the entity.
     * 
     * @param id  The ID of the jewelry item to update.
     * @param dto Data Transfer Object containing updated jewelry details.
     * @return JewelryDTO of the updated jewelry item.
     */
    @Transactional
    public JewelryDTO updateJewelry(Long id, JewelryDTO dto) {
        Jewelry j = jewelryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jewelry not found"));

        // Update common fields
        j.setName(dto.getName());
        j.setMaterial(dto.getMaterial());
        j.setWeight(dto.getWeight());
        j.setPrice(dto.getPrice());
        j.setStockQuantity(dto.getStockQuantity());
        j.setCategory(dto.getCategory());

        JewelryType currentType;
        // Determine the current type of the jewelry item
        if (j instanceof Necklace) {
            currentType = JewelryType.NECKLACE;
        } else if (j instanceof Earring) {
            currentType = JewelryType.EARRING;
        } else if (j instanceof Ring) {
            currentType = JewelryType.RING;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown jewelry type");
        }

        JewelryType newType = dto.getJewelryType();
        if (newType == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jewelry type required");
        }

        // If the type has changed, migrate the entity to the new type
        if (!currentType.equals(newType)) {
            jewelryRepository.delete(j); // Delete the existing entity

            switch (newType) {
                case NECKLACE:
                    // Validate required fields for Necklace
                    if (dto.getLength() == null)
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Length is required for Necklace");
                    Necklace n = new Necklace();
                    n.setId(j.getId()); // Retain the same ID for consistency
                    n.setName(j.getName());
                    n.setMaterial(j.getMaterial());
                    n.setWeight(j.getWeight());
                    n.setPrice(j.getPrice());
                    n.setStockQuantity(j.getStockQuantity());
                    n.setCategory(j.getCategory());
                    n.setLength(dto.getLength());
                    j = n;
                    break;

                case EARRING:
                    // Validate required fields for Earring
                    if (dto.getClaspType() == null || dto.getClaspType().isEmpty())
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clasp Type is required for Earring");
                    Earring e = new Earring();
                    e.setId(j.getId());
                    e.setName(j.getName());
                    e.setMaterial(j.getMaterial());
                    e.setWeight(j.getWeight());
                    e.setPrice(j.getPrice());
                    e.setStockQuantity(j.getStockQuantity());
                    e.setCategory(j.getCategory());
                    e.setClaspType(dto.getClaspType());
                    j = e;
                    break;

                case RING:
                    // Validate required fields for Ring
                    if (dto.getSize() == null || dto.getSize().isEmpty())
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size is required for Ring");
                    Ring r = new Ring();
                    r.setId(j.getId());
                    r.setName(j.getName());
                    r.setMaterial(j.getMaterial());
                    r.setWeight(j.getWeight());
                    r.setPrice(j.getPrice());
                    r.setStockQuantity(j.getStockQuantity());
                    r.setCategory(j.getCategory());
                    r.setSize(dto.getSize());
                    j = r;
                    break;

                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported jewelry type");
            }
        } else {
            // If type is unchanged, update type-specific fields
            switch (currentType) {
                case NECKLACE:
                    // Update length for Necklace
                    if (dto.getLength() == null)
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Length is required for Necklace");
                    ((Necklace) j).setLength(dto.getLength());
                    break;
                case EARRING:
                    // Update claspType for Earring
                    if (dto.getClaspType() == null || dto.getClaspType().isEmpty())
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clasp Type is required for Earring");
                    ((Earring) j).setClaspType(dto.getClaspType());
                    break;
                case RING:
                    // Update size for Ring
                    if (dto.getSize() == null || dto.getSize().isEmpty())
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size is required for Ring");
                    ((Ring) j).setSize(dto.getSize());
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported jewelry type");
            }
        }

        // Save the updated Jewelry entity
        Jewelry saved = jewelryRepository.save(j);
        dto.setId(saved.getId()); // Populate the DTO with the generated ID
        return dto;
    }

    /**
     * Deletes a Jewelry item by ID.
     * 
     * @param id The ID of the jewelry item to delete.
     */
    public void deleteJewelry(Long id) {
        Jewelry j = jewelryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jewelry not found"));
        jewelryRepository.delete(j); // Deletes the jewelry item from the database
    }
}
