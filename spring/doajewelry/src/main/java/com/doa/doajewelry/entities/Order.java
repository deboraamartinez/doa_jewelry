package com.doa.doajewelry.entities;

import com.doa.doajewelry.entities.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an Order in the system.
 * Mapped to the "orders" table in the database.
 */
@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Date of the order creation or acceptance.
     */
    @NotNull
    private LocalDate date;

    /**
     * Status of the order: PENDING, ACCEPTED, DELIVERED, CANCELED, etc.
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;

    /**
     * Many-to-One relationship with Customer.
     * An order is placed by one customer.
     * FetchType.LAZY defers loading the customer until it's accessed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * Many-to-One relationship with Employee.
     * An order is associated with one employee (e.g., Salesperson).
     * FetchType.LAZY defers loading the employee until it's accessed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**
     * Many-to-Many relationship with Jewelry.
     * An order can contain multiple jewelry items, and a jewelry item can be part of multiple orders.
     * Managed via a join table "order_jewelry".
     */
    @ManyToMany
    @JoinTable(
        name = "order_jewelry",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "jewelry_id")
    )
    private Set<Jewelry> items = new HashSet<>();

    /**
     * One-to-Many relationship with Payment.
     * An order can have multiple payments associated with it.
     * 
     * mappedBy = "order" indicates that the 'order' field in Payment owns the relationship.
     * 
     * cascade = CascadeType.ALL propagates all operations to payments.
     * 
     * orphanRemoval = true ensures that removing a payment from the set deletes it from the database.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    // Constructors

    public Order() {
    }

    public Order(LocalDate date, OrderStatus status, Customer customer, Employee employee) {
        this.date = date;
        this.status = status;
        this.customer = customer;
        this.employee = employee;
    }

    // Getters and Setters...

    public Long getId() {
        return id;
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

    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    } 

    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Jewelry> getItems() {
        return items;
    }

    public void setItems(Set<Jewelry> items) {
        this.items = items;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    // Utility methods to manage bidirectional relationships

    /**
     * Adds a jewelry item to the order.
     * 
     * @param jewelry The jewelry item to add.
     */
    public void addItem(Jewelry jewelry) {
        this.items.add(jewelry);
    }

    /**
     * Removes a jewelry item from the order.
     * 
     * @param jewelry The jewelry item to remove.
     */
    public void removeItem(Jewelry jewelry) {
        this.items.remove(jewelry);
    }

    /**
     * Adds a payment to the order.
     * Also sets this order as the owner of the payment.
     * 
     * @param payment The payment to add.
     */
    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setOrder(this);
    }

    /**
     * Removes a payment from the order.
     * Also disassociates this order from the payment.
     * 
     * @param payment The payment to remove.
     */
    public void removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setOrder(null);
    }

    /**
     * Calculates the total amount of the order based on associated jewelry prices.
     * 
     * @return The total amount of the order.
     */
    public Double getTotalAmount() {
        return items.stream()
            .mapToDouble(Jewelry::getPrice)
            .sum();
    }
}
