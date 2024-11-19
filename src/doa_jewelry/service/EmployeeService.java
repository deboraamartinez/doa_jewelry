package doa_jewelry.service;

import doa_jewelry.entity.Employee;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    public Employee createEmployee(Employee employee) throws RepositoryException {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) throws RepositoryException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new doa_jewelry.exception.EntityNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    public List<Employee> getAllEmployees() throws RepositoryException {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(Long id) throws RepositoryException {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(Employee employee) throws RepositoryException {
        return employeeRepository.update(employee);
    }
}
