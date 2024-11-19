package doa_jewelry.entity;

import java.time.LocalDate;

public class Salesperson extends Employee {
    // Without id 
    public Salesperson(String name, String nif, LocalDate hireDate, double salary) {
        super(name, nif, hireDate, salary);
    }

    // With id 
    public Salesperson(Long id, String name, String nif, LocalDate hireDate, double salary) {
        super(name, nif, hireDate, salary);
        this.setId(id); // Setter from Employee
    }

    @Override
    public String toString() {
        return "Manager{" +
                "ID=" + getId() +
                ", Nome='" + getName() + '\'' +
                ", NIF='" + getNif() + '\'' +
                ", Data de Contratação=" + getHireDate() +
                ", Salário=" + getSalary() +
                '}';
    }
}
