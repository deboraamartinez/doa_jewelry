package doa_jewelry.controller;

import doa_jewelry.entity.Employee;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.service.EmployeeService;

import java.util.List;

public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee createEmployee(Employee employee) {
        try {
            return employeeService.createEmployee(employee);
        } catch (RepositoryException e) {
            System.err.println("Error creating employee: " + e.getMessage());
            return null;
        }
    }

    public Employee getEmployeeById(Long id) {
        try {
            return employeeService.getEmployeeById(id);
        } catch (RepositoryException e) {
            System.err.println("Error retrieving employee: " + e.getMessage());
            return null;
        }
    }

    public List<Employee> getAllEmployees() {
        try {
            return employeeService.getAllEmployees();
        } catch (RepositoryException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
            return List.of();
        }
    }

    public void deleteEmployee(Long id) {
        try {
            employeeService.deleteEmployee(id);
        } catch (RepositoryException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }

    public Employee updateEmployee(Employee employee) {
        try {
            return employeeService.updateEmployee(employee);
        } catch (RepositoryException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            return null;
        }
    }

    public void saveAll() {
        try {
            employeeService.saveAll();
        } catch (RepositoryException e) {
            System.err.println("Error saving employees: " + e.getMessage());
        }
    }
}
