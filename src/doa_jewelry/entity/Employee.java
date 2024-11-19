package doa_jewelry.entity;

import java.time.LocalDate;

public abstract class Employee implements Entity<Long> {
    private Long id;
    private String name;
    private String nif;
    private LocalDate hireDate;
    private double salary;

    public Employee(String name, String nif, LocalDate hireDate, double salary) {
        this.name = name;
        this.nif = nif;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNif() {
        return nif;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    };

    public void setNif(String nif) {
        this.nif = nif;
    };

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    };
}
