package com.doa.doajewelry.entities;

import com.doa.doajewelry.entities.enums.JewelryCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Abstract base entity representing Jewelry.
 * Uses joined inheritance strategy.
 * Discriminator column "jewelry_type" distinguishes between subclasses.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED) 
@DiscriminatorColumn(name = "jewelry_type")
@Table(name = "jewelry")
public abstract class Jewelry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String material;

    @NotNull
    private Double weight;

    @NotNull
    private Double price;

    @NotNull
    private Integer stockQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private JewelryCategory category;

    // Constructors

    public Jewelry() {}

    public Jewelry(String name, String material, Double weight, Double price, Integer stockQuantity, JewelryCategory category) {
        this.name = name;
        this.material = material;
        this.weight = weight;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) { 
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMaterial() {
        return material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public JewelryCategory getCategory() {
        return category;
    }
    public void setCategory(JewelryCategory category) {
        this.category = category;
    }
}
