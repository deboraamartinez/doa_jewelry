package com.doa.doajewelry.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDate;

/**
 * Entity representing a Salesperson.
 * Inherits from the abstract Employee class.
 * Uses a discriminator value to distinguish its type in the employees table.
 */
@Entity
@DiscriminatorValue("SALESPERSON")
public class Salesperson extends Employee { 

    // Constructors

    public Salesperson() {}

    public Salesperson(String nif, String name, LocalDate hireDate, Double salary) {
        super(nif, name, hireDate, salary);
    }

    // Getters and Setters

    // Additional methods

    @Override
    public Double getTotalSales() {
        // To be implemented via service and DTO
        return null; // Returns null or throws an exception if accessed directly
    }
}
