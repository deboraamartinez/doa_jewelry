package doa_jewelry.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order implements Entity<Long> {
    private Long id;
    private Customer customer;
    private LocalDate date;
    private List<Jewelry> items;
    private double totalAmount;
    private OrderStatus status;

    public enum OrderStatus {
        DELIVERED, PENDING, ACCEPTED, CANCELED
    }

    public Order(Customer customer, LocalDate date) {
        this.customer = customer;
        this.date = date;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.totalAmount = 0.0;
    }

 
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Jewelry> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addItem(Jewelry jewelry) {
        items.add(jewelry);
        totalAmount += jewelry.getPrice();
    }
}
