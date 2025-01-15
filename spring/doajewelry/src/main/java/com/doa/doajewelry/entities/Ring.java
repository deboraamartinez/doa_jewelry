package com.doa.doajewelry.entities;

import com.doa.doajewelry.entities.enums.JewelryCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

/**
 * Entity representing a Ring.
 * Inherits from the abstract Jewelry class.
 * Uses a discriminator value to distinguish its type in the jewelry table.
 */
@Entity
@DiscriminatorValue("RING")
public class Ring extends Jewelry {
    
    @NotBlank
    private String size; // Specific attribute for Ring

    // Constructors

    public Ring() {}

    public Ring(String name, String material, Double weight, Double price, Integer stockQuantity, JewelryCategory category, String size) {
        super(name, material, weight, price, stockQuantity, category);
        this.size = size;
    }

    // Getters and Setters

    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
}
