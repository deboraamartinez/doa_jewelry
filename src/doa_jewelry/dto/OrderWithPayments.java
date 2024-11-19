package doa_jewelry.dto;

import doa_jewelry.entity.Order;
import doa_jewelry.entity.Payment;

import java.util.List;

public class OrderWithPayments {
    private Order order;
    private List<Payment> payments;

    public OrderWithPayments(Order order, List<Payment> payments) {
        this.order = order;
        this.payments = payments;
    }

    // Getters and Setters

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public List<Payment> getPayments() {
        return payments;
    }
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
