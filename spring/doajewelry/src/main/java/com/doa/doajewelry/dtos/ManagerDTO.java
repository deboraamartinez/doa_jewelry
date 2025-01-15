package com.doa.doajewelry.dtos;

import com.doa.doajewelry.entities.Manager;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Data Transfer Object for Manager.
 */
public class ManagerDTO extends EmployeeDTO {
    
    @NotNull(message = "Sales goal cannot be null")
    private Double salesGoal;

    public ManagerDTO() {
        super();
    }

    public ManagerDTO(Long id, String nif, String name, LocalDate hireDate, Double salary, String employeeType, Double salesGoal) {
        super(id, nif, name, hireDate, salary, employeeType, salesGoal, null);
        this.salesGoal = salesGoal;
    }

    // Getter and Setter for salesGoal

    @Override
    public Double getSalesGoal() {
        return salesGoal;
    }

    @Override
    public void setSalesGoal(Double salesGoal) {
        this.salesGoal = salesGoal;
    }

    /**
     * Creates a ManagerDTO from a Manager entity.
     * @param m the Manager entity.
     * @return a new ManagerDTO.
     */
    public static ManagerDTO fromEntity(Manager m) {
        ManagerDTO dto = new ManagerDTO();
        dto.setId(m.getId());
        dto.setNif(m.getNif());
        dto.setName(m.getName());
        dto.setHireDate(m.getHireDate());
        dto.setSalary(m.getSalary());
        dto.setEmployeeType("MANAGER");
        dto.setSalesGoal(m.getSalesGoal());
        return dto;
    }
}
