package doa_jewelry.entity;

import java.time.LocalDate;

public class Salesperson extends Employee {

    public Salesperson(String name, String nif, LocalDate hireDate, double salary) {
        super(name, nif, hireDate, salary);
    }

    public Salesperson(Long id, String name, String nif, LocalDate hireDate, double salary) {
        super(name, nif, hireDate, salary);
        this.setId(id);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "ID=" + getId() +
                ", Name='" + getName() + '\'' +
                ", NIF='" + getNif() + '\'' +
                ", Hire date=" + getHireDate() +
                ", Salary=" + getSalary() +
                '}';
    }
}
