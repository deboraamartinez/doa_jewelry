package com.doa.doajewelry.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entity representing a Manager.
 * Inherits from the abstract Employee class.
 * Uses a discriminator value to distinguish its type in the employees table.
 */
@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends Employee {
    
    @NotNull
    private Double salesGoal; // Specific attribute for Manager

    // Constructors

    public Manager() {}

    public Manager(String nif, String name, LocalDate hireDate, Double salary, Double salesGoal) {
        super(nif, name, hireDate, salary);
        this.salesGoal = salesGoal;
    }

    // Getters and Setters

    public Double getSalesGoal() {
        return salesGoal;
    }

    public void setSalesGoal(Double salesGoal) {
        this.salesGoal = salesGoal;
    }
    
    @Override
    public Double getTotalSales() {
        // To be implemented via service and DTO
        return null; // Returns null or throws an exception if accessed directly
    }
}
