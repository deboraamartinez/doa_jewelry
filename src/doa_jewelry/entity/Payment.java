package doa_jewelry.entity;

import java.time.LocalDate;

public class Payment implements Entity<Long> {
    private Long id;
    private double amount;
    private LocalDate date;
    private PaymentMethod method;
    private Long orderId;

    public Payment(double amount, LocalDate date, PaymentMethod method, Long orderId) {
        this.amount = amount;
        this.date = date;
        this.method = method;
        this.orderId = orderId;
    }

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
    public void setAmount(double amount) {
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
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                ", method='" + method + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
