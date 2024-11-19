package doa_jewelry.entity;

import java.time.LocalDate;

public class Manager extends Employee {
    private double salesGoal;

    // Construtor sem ID (para criação de novos managers)
    public Manager(String name, String nif, LocalDate hireDate, double salary, double salesGoal) {
        super(name, nif, hireDate, salary);
        this.salesGoal = salesGoal;
    }

    public Manager(Long id, String name, String nif, LocalDate hireDate, double salary, double salesGoal) {
        super(name, nif, hireDate, salary);
        this.setId(id);
        this.salesGoal = salesGoal;
    }

    public double getSalesGoal() {
        return salesGoal;
    }

    public void setSalesGoal(double salesGoal) {
        this.salesGoal = salesGoal;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "ID=" + getId() +
                ", Nome='" + getName() + '\'' +
                ", NIF='" + getNif() + '\'' +
                ", Data de Contratação=" + getHireDate() +
                ", Salário=" + getSalary() +
                ", Goals=" + getSalesGoal() +
                '}';
    }
}
