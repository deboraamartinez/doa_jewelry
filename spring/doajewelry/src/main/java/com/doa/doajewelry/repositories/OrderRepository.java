package com.doa.doajewelry.repositories;

import com.doa.doajewelry.entities.Employee;
import com.doa.doajewelry.entities.Order;
import com.doa.doajewelry.entities.enums.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing Order entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Finds all orders with a specific status.
     * @param status the status of the orders.
     * @return a list of orders.
     */
    List<Order> findAllByStatus(OrderStatus status);

    /**
     * Finds all orders associated with a specific employee.
     * @param employee the employee associated with the orders.
     * @return a list of orders.
     */
    List<Order> findAllByEmployee(Employee employee);
}
