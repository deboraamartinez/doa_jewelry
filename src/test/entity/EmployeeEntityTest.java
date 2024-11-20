package test.entity;

import doa_jewelry.entity.Employee;
import doa_jewelry.entity.Manager;
import doa_jewelry.entity.Salesperson;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class EmployeeEntityTest {

    @Test
    public void testManagerInitialization() {
        Manager manager = new Manager("Alice Smith", "NIF001", LocalDate.of(2020, 1, 1), 5000.0, 100000.0);

        assertEquals("Alice Smith", manager.getName());
        assertEquals("NIF001", manager.getNif());
        assertEquals(LocalDate.of(2020, 1, 1), manager.getHireDate());
        assertEquals(5000.0, manager.getSalary(), 0.01);
        assertEquals(100000.0, manager.getSalesGoal(), 0.01);
    }

    @Test
    public void testSalespersonInitialization() {
        Salesperson salesperson = new Salesperson(2L, "Bob Johnson", "NIF002", LocalDate.of(2021, 5, 15), 4000.0);

        assertEquals(Long.valueOf(2L), salesperson.getId());
        assertEquals("Bob Johnson", salesperson.getName());
        assertEquals("NIF002", salesperson.getNif());
        assertEquals(LocalDate.of(2021, 5, 15), salesperson.getHireDate());
        assertEquals(4000.0, salesperson.getSalary(), 0.01);
    }
}
