package com.doa.doajewelry.repositories;

import com.doa.doajewelry.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByNif(String nif);
}
