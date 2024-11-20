package test.repository;

import doa_jewelry.entity.*;
import doa_jewelry.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class EmployeeRepositoryTest {

    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        employeeRepository = new EmployeeRepository();
        employeeRepository.deleteAll(); 
    }

    @After
    public void tearDown() {
        employeeRepository.deleteAll(); 
    }

    @Test
    public void testSaveEmployee() {
        Manager manager = new Manager("Alice Smith", "NIF001", LocalDate.of(2020, 1, 1), 5000.0, 100000.0);
        Employee savedManager = employeeRepository.save(manager);

        assertNotNull(savedManager.getId());
        assertEquals(1, employeeRepository.findAll().size());
    }

    @Test
    public void testFindById() {
        Salesperson salesperson = new Salesperson(1L, "Bob Johnson", "NIF002", LocalDate.of(2021, 5, 15), 4000.0);
        Employee savedSalesperson = employeeRepository.save(salesperson);

        Employee retrievedSalesperson = employeeRepository.findById(savedSalesperson.getId()).orElse(null);

        assertNotNull(retrievedSalesperson);
        assertEquals(savedSalesperson.getId(), retrievedSalesperson.getId());
    }

    @Test
    public void testDeleteEmployee() {
        Manager manager = new Manager("Alice Smith", "NIF001", LocalDate.of(2020, 1, 1), 5000.0, 100000.0);
        Employee savedManager = employeeRepository.save(manager);

        employeeRepository.deleteById(savedManager.getId());

        assertTrue(employeeRepository.findAll().isEmpty());
    }
}
