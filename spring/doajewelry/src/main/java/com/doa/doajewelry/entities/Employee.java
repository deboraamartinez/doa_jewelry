package com.doa.doajewelry.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Abstract base entity representing an Employee.
 * Uses single-table inheritance strategy.
 * Discriminator column "employee_type" distinguishes between subclasses.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_type")
@Table(name = "employees")
public abstract class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String nif; // Tax Identification Number

    @NotBlank
    private String name;

    @NotNull
    private LocalDate hireDate;

    @NotNull
    private Double salary;

    // Constructors

    public Employee() {}

    public Employee(String nif, String name, LocalDate hireDate, Double salary) {
        this.nif = nif;
        this.name = name;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    } 

    public Double getSalary() {
        return salary;
    }
    
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * Abstract method to get total sales.
     * Must be implemented by subclasses.
     * 
     * @return Total sales amount.
     */
    public abstract Double getTotalSales();
}
