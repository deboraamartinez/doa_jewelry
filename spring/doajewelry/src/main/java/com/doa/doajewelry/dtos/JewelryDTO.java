package com.doa.doajewelry.dtos;

import com.doa.doajewelry.entities.Earring;
import com.doa.doajewelry.entities.Jewelry;
import com.doa.doajewelry.entities.Necklace;
import com.doa.doajewelry.entities.Ring;
import com.doa.doajewelry.entities.enums.JewelryCategory;
import com.doa.doajewelry.entities.enums.JewelryType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Jewelry.
 */
public class JewelryDTO {

    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Material cannot be empty")
    @Size(max = 50, message = "Material cannot exceed 50 characters")
    private String material;

    @NotNull(message = "Weight cannot be null")
    @Positive(message = "Weight must be a positive value")
    private Double weight;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Stock quantity cannot be null")
    @Positive(message = "Stock quantity must be a positive number")
    private Integer stockQuantity;

    @NotNull(message = "Category cannot be null")
    private JewelryCategory category;

    private JewelryType jewelryType;

    // Specific attributes
    @Positive(message = "Length must be a positive value")
    private Double length; // For Necklace

    @Size(max = 30, message = "Clasp type cannot exceed 30 characters")
    private String claspType; // For Earring

    @Size(max = 10, message = "Size cannot exceed 10 characters")
    private String size; // For Ring

    public JewelryDTO() {}

    // Getters and Setters

    public static JewelryDTO fromEntity(Jewelry j) {
        if (j == null) {
            return null;
        }

        JewelryDTO dto = new JewelryDTO();
        dto.setId(j.getId());
        dto.setName(j.getName());
        dto.setMaterial(j.getMaterial());
        dto.setWeight(j.getWeight());
        dto.setPrice(j.getPrice());
        dto.setStockQuantity(j.getStockQuantity());
        dto.setCategory(j.getCategory());

        // Determine subclass type
        if (j instanceof Necklace) {
            dto.setJewelryType(JewelryType.NECKLACE);
            dto.setLength(((Necklace) j).getLength());
        } else if (j instanceof Earring) {
            dto.setJewelryType(JewelryType.EARRING);
            dto.setClaspType(((Earring) j).getClaspType());
        } else if (j instanceof Ring) {
            dto.setJewelryType(JewelryType.RING);
            dto.setSize(((Ring) j).getSize());
        }

        return dto;
    }

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

    public JewelryType getJewelryType() {
        return jewelryType;
    }

    public void setJewelryType(JewelryType jewelryType) {
        this.jewelryType = jewelryType;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getClaspType() {
        return claspType;
    }

    public void setClaspType(String claspType) {
        this.claspType = claspType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
