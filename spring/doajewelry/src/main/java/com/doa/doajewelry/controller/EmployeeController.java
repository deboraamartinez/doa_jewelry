package com.doa.doajewelry.controller;

import com.doa.doajewelry.dtos.EmployeeDTO;
import com.doa.doajewelry.services.EmployeeService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing Employees.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /**
     * Creates a new employee (Manager or Salesperson).
     * @param dto the employee data.
     * @return the created EmployeeDTO.
     */
    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO dto) {
        return service.createEmployee(dto);
    }

    /**
     * Retrieves all employees with additional information, if applicable.
     * @return a list of EmployeeDTOs.
     */
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return service.getAllEmployees();
    }

    /**
     * Retrieves a specific employee by ID with additional information, if applicable.
     * @param id the employee ID.
     * @return the corresponding EmployeeDTO.
     */
    @GetMapping("/{id}")
    public EmployeeDTO getEmployee(@PathVariable Long id) {
        return service.getEmployee(id);
    }

    /**
     * Updates an existing employee (Manager or Salesperson).
     * @param id the employee ID.
     * @param dto the updated employee data.
     * @return the updated EmployeeDTO.
     */
    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO dto) {
        return service.updateEmployee(id, dto);
    }

    /**
     * Deletes an employee by ID.
     * @param id the employee ID.
     */
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
}
