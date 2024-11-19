package doa_jewelry.entity;

import java.time.LocalDate;

public class Payment implements Entity<Long> {
    private Long id;
    private double amount;
    private LocalDate date;
    private PaymentMethod method;
    private Order order;

    public enum PaymentMethod {
        CREDIT_CARD, BANK_TRANSFER, CASH
    }

    public Payment(double amount, LocalDate date, PaymentMethod method, Order order) {
        this.amount = amount;
        this.date = date;
        this.method = method;
        this.order = order;
    }

    // Getters and Setters
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public Order getOrder() {
        return order;
    }
}
