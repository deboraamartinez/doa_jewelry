package doa_jewelry.controller;

import doa_jewelry.entity.Employee;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.service.EmployeeService;

import java.util.List;

// Controller for managing employee-related operations
public class EmployeeController {
    private final EmployeeService employeeService;

    // Constructor to initialize the EmployeeService dependency
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Create a new employee and handle potential exceptions
    public Employee createEmployee(Employee employee) {
        try {
            return employeeService.createEmployee(employee); // Call the service to create the employee
        } catch (RepositoryException e) {
            System.err.println("Error creating employee: " + e.getMessage());
            return null;
        }
    }

    // Retrieve an employee by its ID and handle potential exceptions
    public Employee getEmployeeById(Long id) {
        try {
            return employeeService.getEmployeeById(id); // Call the service to get the employee by ID
        } catch (RepositoryException e) {
            System.err.println("Error retrieving employee: " + e.getMessage());
            return null;
        }
    }

    // Retrieve all employees and handle potential exceptions
    public List<Employee> getAllEmployees() {
        try {
            return employeeService.getAllEmployees(); // Call the service to get all employees
        } catch (RepositoryException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
            return List.of(); // Return an empty list if an error occurs
        }
    }

    // Delete an employee by its ID and handle potential exceptions
    public void deleteEmployee(Long id) {
        try {
            employeeService.deleteEmployee(id); // Call the service to delete the employee
        } catch (RepositoryException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }

    // Update an existing employee and handle potential exceptions
    public Employee updateEmployee(Employee employee) {
        try {
            return employeeService.updateEmployee(employee); // Call the service to update the employee
        } catch (RepositoryException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            return null;
        }
    }

    // Save all employees to the storage and handle potential exceptions
    public void saveAll() {
        try {
            employeeService.saveAll(); // Call the service to save all employees
        } catch (RepositoryException e) {
            System.err.println("Error saving employees: " + e.getMessage());
        }
    }
}
