package doa_jewelry.service;

import doa_jewelry.entity.Employee;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.repository.EmployeeRepository;

import java.util.List;

// Service responsible for handling business logic related to employees
public class EmployeeService {
    private final EmployeeRepository employeeRepository; // Repository to manage employee data

    // Constructor to initialize the service with the employee repository
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Creates a new employee and saves it in the repository
    public Employee createEmployee(Employee employee) throws RepositoryException {
        return employeeRepository.save(employee);
    }

    // Retrieves an employee by ID; throws an exception if the employee does not exist
    public Employee getEmployeeById(Long id) throws RepositoryException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new doa_jewelry.exception.EntityNotFoundException("Employee not found with id: " + id));
    }

    // Retrieves all employees from the repository
    public List<Employee> getAllEmployees() throws RepositoryException {
        return employeeRepository.findAll();
    }

    // Deletes an employee by ID
    public void deleteEmployee(Long id) throws RepositoryException {
        employeeRepository.deleteById(id);
    }

    // Updates an existing employee's information in the repository
    public Employee updateEmployee(Employee employee) throws RepositoryException {
        return employeeRepository.update(employee);
    }

    // Saves all employee data to persistent storage
    public void saveAll() throws RepositoryException {
        employeeRepository.saveAll();
    }
}
