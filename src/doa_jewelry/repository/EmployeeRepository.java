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

public class EmployeeRepository extends MyCrudRepository<Employee> {

    private static final String FILE_PATH = "data/employees.csv";
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        loadFromFile();
    }

    @Override
    public Employee save(Employee employee) throws RepositoryException {
        boolean nifExists = employees.stream()
                .anyMatch(e -> e.getNif().equalsIgnoreCase(employee.getNif()));
        if (nifExists) {
            throw new EntityAlreadyExistsException("Já existe um funcionário com o NIF " + employee.getNif());
        }
        if (employee.getId() == null) {
            Long newId = employees.stream()
                    .mapToLong(e -> e.getId() != null ? e.getId() : 0L)
                    .max()
                    .orElse(0L) + 1;
            employee.setId(newId);
        }

        employees.add(employee);
        saveToFile();
        return employee;
    }

    @Override
    public Employee update(Employee employee) throws RepositoryException {
        if (employee.getId() == null) {
            throw new RepositoryException("O ID do funcionário não pode ser nulo para atualização.");
        }

        Optional<Employee> existingEmployeeOpt = findById(employee.getId());
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();

            existingEmployee.setName(employee.getName());
            existingEmployee.setNif(employee.getNif());
            existingEmployee.setHireDate(employee.getHireDate());
            existingEmployee.setSalary(employee.getSalary());

            if (existingEmployee instanceof Manager && employee instanceof Manager) {
                ((Manager) existingEmployee).setSalesGoal(((Manager) employee).getSalesGoal());
            } else if (existingEmployee instanceof Salesperson && employee instanceof Salesperson) {
                // ((Salesperson) existingEmployee).setSomeField(((Salesperson) employee).getSomeField());
            } else {
                throw new RepositoryException("Tipo de funcionário não corresponde ao existente.");
            }

            saveToFile();
            return existingEmployee;
        } else {
            throw new EntityNotFoundException("Funcionário não encontrado com o ID: " + employee.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        Optional<Employee> employeeOpt = findById(id);
        if (employeeOpt.isPresent()) {
            employees.remove(employeeOpt.get());
            saveToFile();
        } else {
            throw new EntityNotFoundException("Funcionário não encontrado com o ID: " + id);
        }
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employees.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) {
                    throw new RuntimeException("Dados insuficientes na linha: " + line);
                }

                String type = data[0].trim();
                Long id = Long.parseLong(data[1].trim());
                String name = data[2].trim();
                String nif = data[3].trim();
                LocalDate hireDate = LocalDate.parse(data[4].trim());
                double salary = Double.parseDouble(data[5].trim());

                Employee employee;
                switch (type.toLowerCase()) {
                    case "manager":
                        if (data.length < 7) {
                            throw new RuntimeException("Dados insuficientes para Manager na linha: " + line);
                        }
                        double salesGoal = Double.parseDouble(data[6].trim());
                        employee = new Manager(id, name, nif, hireDate, salary, salesGoal);
                        break;
                    case "salesperson":
                        employee = new Salesperson(id, name, nif, hireDate, salary);
                        // Se Salesperson tiver campos específicos, parse e configure-os aqui
                        break;
                    default:
                        throw new RuntimeException("Tipo desconhecido de funcionário: " + type);
                }

                employees.add(employee);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar funcionários do arquivo", e);
        }
    }

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
            throw new RepositoryException("Erro ao salvar funcionários no arquivo", e);
        }
    }
}
