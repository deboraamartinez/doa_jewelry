package com.doa.doajewelry.dtos;

import com.doa.doajewelry.entities.Jewelry;
import com.doa.doajewelry.entities.Order;
import com.doa.doajewelry.entities.enums.OrderStatus;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Data Transfer Object for Order entity.
 */
public class OrderDTO {

    private Long id;
    private LocalDate date;
    private OrderStatus status;
    private Long customerId;
    private Long employeeId;
    private Set<Long> jewelryIds;

    public OrderDTO() {}

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Set<Long> getJewelryIds() {
        return jewelryIds;
    }

    public void setJewelryIds(Set<Long> jewelryIds) {
        this.jewelryIds = jewelryIds;
    }

    /**
     * Creates an OrderDTO from an Order entity.
     * @param o the Order entity to convert.
     * @return a new OrderDTO reflecting the given entity.
     */
    public static OrderDTO fromEntity(Order o) {
        if (o == null) return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(o.getId());
        dto.setDate(o.getDate());
        dto.setStatus(o.getStatus());

        if (o.getCustomer() != null) {
            dto.setCustomerId(o.getCustomer().getId());
        }

        if (o.getEmployee() != null) {
            dto.setEmployeeId(o.getEmployee().getId());
        }

        if (o.getItems() != null) {
            dto.setJewelryIds(
                o.getItems().stream()
                    .map(Jewelry::getId)
                    .collect(Collectors.toSet())
            );
        }

        return dto;
    }
}
