package com.doa.doajewelry.entities;

import com.doa.doajewelry.entities.enums.PaymentMethod;
import com.doa.doajewelry.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entity representing a Payment.
 * Mapped to the "payments" table in the database.
 */
@Entity
@Table(name = "payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double amount;

    @NotNull
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentStatus status;

    // Relationships

    /**
     * Many-to-One relationship with Order.
     * A payment is associated with one order.
     * FetchType.LAZY defers loading the order until it's accessed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // Constructors

    public Payment() {}

    public Payment(Double amount, LocalDate date, PaymentMethod method, PaymentStatus status, Order order) {
        this.amount = amount;
        this.date = date;
        this.method = method;
        this.status = status;
        this.order = order;
    }

    // Getters and Setters...

    public Long getId() {
        return id;
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

    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    } 
}
