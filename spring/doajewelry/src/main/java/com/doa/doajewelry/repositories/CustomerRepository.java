package com.doa.doajewelry.repositories;

import com.doa.doajewelry.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByNif(String nif);
}
