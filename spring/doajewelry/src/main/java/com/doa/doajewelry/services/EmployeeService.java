package com.doa.doajewelry.services;

import com.doa.doajewelry.dtos.EmployeeDTO;
import com.doa.doajewelry.entities.Employee;
import com.doa.doajewelry.entities.Manager;
import com.doa.doajewelry.entities.Order;
import com.doa.doajewelry.entities.Salesperson;
import com.doa.doajewelry.repositories.EmployeeRepository;
import com.doa.doajewelry.repositories.OrderRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service layer for managing Employee operations.
 * Handles creation, retrieval, updating, and deletion of employees.
 * Supports different types of employees such as Managers and Salespersons.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;

    /**
     * Constructor-based dependency injection for repositories.
     * 
     * @param employeeRepository Repository for Employee entities.
     * @param orderRepository    Repository for Order entities.
     */
    public EmployeeService(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        this.employeeRepository = employeeRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Creates a new Employee (Manager or Salesperson).
     * Validates uniqueness of NIF before creation.
     * 
     * @param dto Data Transfer Object containing employee details.
     * @return EmployeeDTO of the created employee.
     */
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        // Check if NIF already exists to ensure uniqueness
        if (employeeRepository.existsByNif(dto.getNif())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee NIF already exists");
        }

        Employee e;

        // Determine the type of employee to create based on employeeType field
        if ("MANAGER".equalsIgnoreCase(dto.getEmployeeType())) {
            // Create a Manager instance
            Manager m = new Manager();
            m.setNif(dto.getNif());
            m.setName(dto.getName());
            m.setHireDate(dto.getHireDate() == null ? LocalDate.now() : dto.getHireDate()); // Default to current date if not provided
            m.setSalary(dto.getSalary());
            m.setSalesGoal(dto.getSalesGoal());
            e = m;
        } else if ("SALESPERSON".equalsIgnoreCase(dto.getEmployeeType())) {
            // Create a Salesperson instance
            Salesperson s = new Salesperson();
            s.setNif(dto.getNif());
            s.setName(dto.getName());
            s.setHireDate(dto.getHireDate() == null ? LocalDate.now() : dto.getHireDate()); // Default to current date if not provided
            s.setSalary(dto.getSalary());
            // Additional Salesperson-specific fields can be set here
            e = s;
        } else {
            // If employeeType is invalid, throw an exception
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid employee type");
        }

        // Save the Employee entity to the database
        Employee saved = employeeRepository.save(e);

        // Populate the DTO with the generated ID
        dto.setId(saved.getId());

        // For Salespersons, initialize totalSales (e.g., 0.0)
        if (saved instanceof Salesperson) {
            dto.setTotalSales(0.0);
        }

        return dto;
    }

    /**
     * Retrieves all Employees with additional information based on their type.
     * 
     * @return List of EmployeeDTOs representing all employees.
     */
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(e -> {
                    EmployeeDTO dto = new EmployeeDTO();
                    dto.setId(e.getId());
                    dto.setNif(e.getNif());
                    dto.setName(e.getName());
                    dto.setHireDate(e.getHireDate());
                    dto.setSalary(e.getSalary());

                    if (e instanceof Manager) {
                        Manager m = (Manager) e;
                        dto.setEmployeeType("MANAGER");
                        dto.setSalesGoal(m.getSalesGoal());
                    } else if (e instanceof Salesperson) {
                        Salesperson s = (Salesperson) e;
                        dto.setEmployeeType("SALESPERSON");
                        // Calculate totalSales for the Salesperson
                        Double totalSales = calculateTotalSales(s);
                        dto.setTotalSales(totalSales);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single Employee by ID with additional information based on type.
     * 
     * @param id The ID of the employee to retrieve.
     * @return EmployeeDTO of the found employee.
     */
    public EmployeeDTO getEmployee(Long id) {
        Employee e = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setNif(e.getNif());
        dto.setName(e.getName());
        dto.setHireDate(e.getHireDate());
        dto.setSalary(e.getSalary());

        if (e instanceof Manager) {
            Manager m = (Manager) e;
            dto.setEmployeeType("MANAGER");
            dto.setSalesGoal(m.getSalesGoal());
        } else if (e instanceof Salesperson) {
            Salesperson s = (Salesperson) e;
            dto.setEmployeeType("SALESPERSON");
            // Calculate totalSales for the Salesperson
            Double totalSales = calculateTotalSales(s);
            dto.setTotalSales(totalSales);
        }

        return dto;
    }

    /**
     * Updates an existing Employee (Manager or Salesperson).
     * Handles conversion between employee types if necessary.
     * 
     * @param id  The ID of the employee to update.
     * @param dto Data Transfer Object containing updated employee details.
     * @return EmployeeDTO of the updated employee.
     */
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee e = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        // If NIF is being changed, validate its uniqueness
        if (!e.getNif().equals(dto.getNif()) && employeeRepository.existsByNif(dto.getNif())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee NIF already exists");
        }

        // Update common fields
        e.setNif(dto.getNif());
        e.setName(dto.getName());
        e.setHireDate(dto.getHireDate());
        e.setSalary(dto.getSalary());

        String newType = dto.getEmployeeType().toUpperCase(); // Normalize to uppercase for comparison

        if (e instanceof Manager && "SALESPERSON".equals(newType)) {
            // Convert Manager to Salesperson
            Salesperson s = new Salesperson();
            s.setNif(e.getNif());
            s.setName(e.getName());
            s.setHireDate(e.getHireDate());
            s.setSalary(e.getSalary());
            // Set additional Salesperson-specific fields here if any

            // Save the new Salesperson
            Employee saved = employeeRepository.save(s);

            dto.setId(saved.getId());
            dto.setEmployeeType("SALESPERSON");
            dto.setTotalSales(calculateTotalSales(s));

            return dto;
        } else if (e instanceof Salesperson && "MANAGER".equals(newType)) {
            // Convert Salesperson to Manager
            Manager m = new Manager();
            m.setNif(e.getNif());
            m.setName(e.getName());
            m.setHireDate(e.getHireDate());
            m.setSalary(e.getSalary());
            m.setSalesGoal(dto.getSalesGoal());

            // Save the new Manager
            Employee saved = employeeRepository.save(m);

            dto.setId(saved.getId());
            dto.setEmployeeType("MANAGER");
            dto.setSalesGoal(m.getSalesGoal());

            return dto;
        } else {
            // Maintain the current type and update specific fields
            if (e instanceof Manager) {
                Manager m = (Manager) e;
                m.setSalesGoal(dto.getSalesGoal());
            }
            // No additional fields to update for Salesperson beyond common fields
            employeeRepository.save(e);
        }

        // Update the DTO with updated information
        if (e instanceof Salesperson) {
            Salesperson s = (Salesperson) e;
            dto.setEmployeeType("SALESPERSON");
            dto.setTotalSales(calculateTotalSales(s));
        } else if (e instanceof Manager) {
            dto.setEmployeeType("MANAGER");
        }

        return dto;
    }

    /**
     * Deletes an Employee by ID.
     * 
     * @param id The ID of the employee to delete.
     */
    public void deleteEmployee(Long id) {
        Employee e = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        employeeRepository.delete(e); // Deletes the employee from the database
    }

    /**
     * Calculates the total sales for a Salesperson by summing up the total amounts of their associated orders.
     * 
     * @param s The Salesperson for whom to calculate total sales.
     * @return The total sales amount.
     */
    private Double calculateTotalSales(Salesperson s) {
        List<Order> orders = orderRepository.findAllByEmployee(s); // Retrieves all orders associated with the salesperson
        return orders.stream()
                .mapToDouble(Order::getTotalAmount) // Sums up the total amounts of these orders
                .sum();
    }
}
