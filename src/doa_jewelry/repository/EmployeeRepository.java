package doa_jewelry.repository;

import doa_jewelry.entity.Employee;
import doa_jewelry.entity.Manager;
import doa_jewelry.entity.Salesperson;
import doa_jewelry.exception.EntityAlreadyExistsException;
import doa_jewelry.exception.EntityNotFoundException;
import doa_jewelry.exception.RepositoryException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Repository for managing employee data, including persistence to a CSV file
public class EmployeeRepository extends MyCrudRepository<Employee> {

    private static final String FILE_PATH = "data/employees.csv"; // Path to the CSV file for employee data
    private final List<Employee> employees = new ArrayList<>(); // In-memory list of employees

    // Constructor that loads employees from the CSV file into memory
    public EmployeeRepository() {
        loadFromFile();
    }

    @Override
    public Employee save(Employee employee) throws RepositoryException {
        // Check if an employee with the same NIF already exists
        boolean nifExists = employees.stream()
                .anyMatch(e -> e.getNif().equalsIgnoreCase(employee.getNif()));
        if (nifExists) {
            throw new EntityAlreadyExistsException("Employee with NIF " + employee.getNif() + " already exists.");
        }

        // Assign a new ID if the employee doesn't have one
        if (employee.getId() == null) {
            Long newId = employees.stream()
                    .mapToLong(e -> e.getId() != null ? e.getId() : 0L)
                    .max()
                    .orElse(0L) + 1;
            employee.setId(newId);
        }

        // Add the employee to the in-memory list
        employees.add(employee);
        return employee;
    }

    @Override
    public Employee update(Employee employee) throws RepositoryException {
        // Ensure the employee ID is not null
        if (employee.getId() == null) {
            throw new RepositoryException("The ID cannot be null for updating an employee.");
        }

        // Find the existing employee by ID
        Optional<Employee> existingEmployeeOpt = findById(employee.getId());
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();

            // Update the employee's basic information
            existingEmployee.setName(employee.getName());
            existingEmployee.setNif(employee.getNif());
            existingEmployee.setHireDate(employee.getHireDate());
            existingEmployee.setSalary(employee.getSalary());

            // Update specific attributes based on the employee type
            if (existingEmployee instanceof Manager && employee instanceof Manager) {
                ((Manager) existingEmployee).setSalesGoal(((Manager) employee).getSalesGoal());
            } else if (!(existingEmployee.getClass().equals(employee.getClass()))) {
                throw new RepositoryException("Employee type does not match the existing one.");
            }

            return existingEmployee;
        } else {
            throw new EntityNotFoundException("Employee not found with ID: " + employee.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        // Remove the employee with the specified ID
        Optional<Employee> employeeOpt = findById(id);
        if (employeeOpt.isPresent()) {
            employees.remove(employeeOpt.get());
        } else {
            throw new EntityNotFoundException("Employee not found with ID: " + id);
        }
    }

    @Override
    public List<Employee> findAll() {
        // Return a copy of the in-memory list
        return new ArrayList<>(employees);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        // Find an employee by their ID
        return employees.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    // Loads employees from the CSV file into memory
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) {
                    throw new RuntimeException("Insufficient data in line: " + line);
                }

                // Extract employee data
                String type = data[0].trim();
                Long id = Long.parseLong(data[1].trim());
                String name = data[2].trim();
                String nif = data[3].trim();
                LocalDate hireDate = LocalDate.parse(data[4].trim());
                double salary = Double.parseDouble(data[5].trim());

                // Determine the employee type and create the corresponding object
                Employee employee;
                switch (type.toLowerCase()) {
                    case "manager":
                        if (data.length < 7) {
                            throw new RuntimeException("Insufficient data for Manager in line: " + line);
                        }
                        double salesGoal = Double.parseDouble(data[6].trim());
                        employee = new Manager(id, name, nif, hireDate, salary, salesGoal);
                        break;
                    case "salesperson":
                        employee = new Salesperson(id, name, nif, hireDate, salary);
                        break;
                    default:
                        throw new RuntimeException("Unknown employee type: " + type);
                }

                employees.add(employee);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading employees from file", e);
        }
    }

    // Saves the in-memory list of employees to the CSV file
    private void saveToFile() throws RepositoryException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee employee : employees) {
                StringBuilder sb = new StringBuilder();
                sb.append(employee.getClass().getSimpleName()).append(",")
                        .append(employee.getId()).append(",")
                        .append(employee.getName()).append(",")
                        .append(employee.getNif()).append(",")
                        .append(employee.getHireDate()).append(",")
                        .append(employee.getSalary());

                if (employee instanceof Manager) {
                    Manager manager = (Manager) employee;
                    sb.append(",").append(manager.getSalesGoal());
                }

                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Error saving employees to file", e);
        }
    }

    // Saves all employee data to the CSV file
    public void saveAll() throws RepositoryException {
        saveToFile();
    }

    // Deletes all employees from memory and clears the CSV file
    public void deleteAll() {
        employees.clear();
        saveToFile();
    }
}
