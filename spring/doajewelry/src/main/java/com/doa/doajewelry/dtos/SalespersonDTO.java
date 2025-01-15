package com.doa.doajewelry.dtos;

import com.doa.doajewelry.entities.Salesperson;
import java.time.LocalDate;

/**
 * Data Transfer Object para Salesperson.
 */
public class SalespersonDTO extends EmployeeDTO {
    
    public SalespersonDTO() {
        super();
    }

    public SalespersonDTO(Long id, String nif, String name, LocalDate hireDate, Double salary, String employeeType, Double totalSales) {
        super(id, nif, name, hireDate, salary, employeeType, null, totalSales);
    }

      /**
     * Creates a SalespersonDTO from a Salesperson entity and total sales.
     * @param s the Salesperson entity.
     * @param totalSales the total sales.
     * @return a new SalespersonDTO.
     */
    public static SalespersonDTO fromEntity(Salesperson s, Double totalSales) {
        SalespersonDTO dto = new SalespersonDTO();
        dto.setId(s.getId());
        dto.setNif(s.getNif());
        dto.setName(s.getName());
        dto.setHireDate(s.getHireDate());
        dto.setSalary(s.getSalary());
        dto.setEmployeeType("SALESPERSON");
        dto.setTotalSales(totalSales != null ? totalSales : 0.0);
        return dto;
    }
}
