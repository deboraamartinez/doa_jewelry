package com.doa.doajewelry.dtos;

import com.doa.doajewelry.entities.Payment;
import com.doa.doajewelry.entities.enums.PaymentMethod;
import com.doa.doajewelry.entities.enums.PaymentStatus;
import java.time.LocalDate;

/**
 * Data Transfer Object for the Payment entity.
 */
public class PaymentDTO {
    private Long id;
    private Double amount;
    private LocalDate date;
    private PaymentMethod method;
    private PaymentStatus status;
    private Long orderId;

    public PaymentDTO() {}

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public PaymentStatus getStatus() { 
        return status;
    }

    public void setStatus(PaymentStatus status) { 
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * Converts a Payment entity to a PaymentDTO.
     * @param p the Payment entity.
     * @return the corresponding PaymentDTO.
     */
    public static PaymentDTO fromEntity(Payment p) {
        if (p == null) return null;

        PaymentDTO dto = new PaymentDTO();
        dto.setId(p.getId());
        dto.setAmount(p.getAmount());
        dto.setDate(p.getDate());
        dto.setMethod(p.getMethod());
        dto.setStatus(p.getStatus());
        if (p.getOrder() != null) {
            dto.setOrderId(p.getOrder().getId());
        }
        return dto;
    }
}
