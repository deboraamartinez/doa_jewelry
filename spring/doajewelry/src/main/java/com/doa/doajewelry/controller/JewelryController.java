package com.doa.doajewelry.controller;

import com.doa.doajewelry.dtos.JewelryDTO;
import com.doa.doajewelry.entities.enums.JewelryType;
import com.doa.doajewelry.services.JewelryService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jewelry")
public class JewelryController {
    private final JewelryService service;

    public JewelryController(JewelryService service) {
        this.service = service;
    }

    /**
     * Creates a new jewelry item.
     * @param dto the jewelry data.
     * @return the created JewelryDTO.
     */
    @PostMapping
    public JewelryDTO create(@RequestBody JewelryDTO dto) {
        return service.createJewelry(dto);
    }

    /**
     * Retrieves all jewelry items.
     * @return a list of JewelryDTOs.
     */
    @GetMapping
    public List<JewelryDTO> getAll() {
        return service.getAllJewelry();
    }

    /**
     * Retrieves jewelry items by type.
     * @param type the type of jewelry.
     * @return a list of JewelryDTOs matching the specified type.
     */
    @GetMapping("/type/{type}")
    public List<JewelryDTO> getByType(@PathVariable JewelryType type) {
        return service.getAllByType(type);
    }

    /**
     * Retrieves a specific jewelry item by ID.
     * @param id the jewelry ID.
     * @return the corresponding JewelryDTO.
     */
    @GetMapping("/{id}")
    public JewelryDTO getOne(@PathVariable Long id) {
        return service.getJewelry(id);
    }

    /**
     * Updates an existing jewelry item.
     * @param id the jewelry ID.
     * @param dto the updated jewelry data.
     * @return the updated JewelryDTO.
     */
    @PutMapping("/{id}")
    public JewelryDTO update(@PathVariable Long id, @RequestBody JewelryDTO dto) {
        return service.updateJewelry(id, dto);
    }

    /**
     * Deletes a jewelry item by ID.
     * @param id the jewelry ID.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteJewelry(id);
    }
}
