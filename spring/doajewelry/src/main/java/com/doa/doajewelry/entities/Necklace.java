package com.doa.doajewelry.entities;

import com.doa.doajewelry.entities.enums.JewelryCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

/**
 * Entity representing a Necklace.
 * Inherits from the abstract Jewelry class.
 * Uses a discriminator value to distinguish its type in the jewelry table.
 */
@Entity
@DiscriminatorValue("NECKLACE")
public class Necklace extends Jewelry {
    
    @NotNull
    private Double length; // Specific attribute for Necklace

    // Constructors

    public Necklace() {}

    public Necklace(String name, String material, Double weight, Double price, Integer stockQuantity, JewelryCategory category, Double length) {
        super(name, material, weight, price, stockQuantity, category);
        this.length = length;
    }

    // Getters and Setters

    public Double getLength() {
        return length;
    }
    public void setLength(Double length) {
        this.length = length;
    }
}
