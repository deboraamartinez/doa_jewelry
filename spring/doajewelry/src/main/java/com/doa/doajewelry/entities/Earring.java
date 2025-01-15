package com.doa.doajewelry.entities;

import com.doa.doajewelry.entities.enums.JewelryCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

/**
 * Entity representing an Earring.
 * Inherits from the abstract Jewelry class.
 * Uses single-table inheritance with a discriminator value.
 */
@Entity
@DiscriminatorValue("EARRING")
public class Earring extends Jewelry {
    
    @NotBlank
    private String claspType; // Specific attribute for Earring

    public Earring() {}

    public Earring(String name, String material, Double weight, Double price, Integer stockQuantity, JewelryCategory category, String claspType) {
        super(name, material, weight, price, stockQuantity, category);
        this.claspType = claspType;
    }

    public String getClaspType() {
        return claspType;
    }
    public void setClaspType(String claspType) {
        this.claspType = claspType;
    }
}
