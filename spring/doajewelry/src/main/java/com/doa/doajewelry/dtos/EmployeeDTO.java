package com.doa.doajewelry.dtos;

import com.doa.doajewelry.entities.Employee;
import com.doa.doajewelry.entities.Manager;
import com.doa.doajewelry.entities.Salesperson;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Data Transfer Object for Employee.
 */
public class EmployeeDTO {
    
    private Long id;

    @NotBlank(message = "NIF cannot be empty")
    private String nif;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Hire date cannot be null")
    private LocalDate hireDate;

    @NotNull(message = "Salary cannot be null")
    private Double salary;

    @NotBlank(message = "Employee type cannot be empty")
    private String employeeType; // "MANAGER" or "SALESPERSON"

    // Specific field for Manager
    private Double salesGoal;

    // Specific field for Salesperson
    private Double totalSales;

    // Constructors
    public EmployeeDTO() {}

    public EmployeeDTO(Long id, String nif, String name, LocalDate hireDate, Double salary, String employeeType, Double salesGoal, Double totalSales) {
        this.id = id;
        this.nif = nif;
        this.name = name;
        this.hireDate = hireDate;
        this.salary = salary;
        this.employeeType = employeeType;
        this.salesGoal = salesGoal;
        this.totalSales = totalSales;
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

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public Double getSalesGoal() {
        return salesGoal;
    }

    public void setSalesGoal(Double salesGoal) {
        this.salesGoal = salesGoal;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * Method to convert from entity to DTO.
     * @param e the Employee entity.
     * @param totalSales the total sales (applicable for Salesperson).
     * @return the corresponding EmployeeDTO.
     */
    public static EmployeeDTO fromEntity(Employee e, Double totalSales) {
        if (e instanceof Salesperson) {
            Salesperson s = (Salesperson) e;
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(s.getId());
            dto.setNif(s.getNif());
            dto.setName(s.getName());
            dto.setHireDate(s.getHireDate());
            dto.setSalary(s.getSalary());
            dto.setEmployeeType("SALESPERSON");
            dto.setTotalSales(totalSales != null ? totalSales : 0.0);
            return dto;
        } else if (e instanceof Manager) {
            Manager m = (Manager) e;
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(m.getId());
            dto.setNif(m.getNif());
            dto.setName(m.getName());
            dto.setHireDate(m.getHireDate());
            dto.setSalary(m.getSalary());
            dto.setEmployeeType("MANAGER");
            dto.setSalesGoal(m.getSalesGoal());
            return dto;
        }
        return null;
    }
}
