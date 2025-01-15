package com.doa.doajewelry.entities;

import com.doa.doajewelry.entities.embedded.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Customer.
 * Mapped to the "customers" table in the database.
 */
@Entity
@Table(name = "customers")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 15)
    @Column(unique = true, nullable = false)
    private String nif; // Tax Identification Number

    @NotBlank
    private String name;

    @Email
    private String email;

    private String phoneNumber;

    /**
     * Embedded Address object.
     * The Address fields are part of the Customer table.
     */
    @Embedded
    private Address address;

    // Relationships

    /**
     * One-to-Many relationship with Order.
     * A customer can have multiple orders.
     * 
     * mappedBy = "customer" indicates that the 'customer' field in Order owns the relationship.
     * 
     * cascade = CascadeType.ALL propagates all operations (persist, merge, remove, etc.) to orders.
     * 
     * orphanRemoval = true ensures that removing an order from the set deletes it from the database.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    // Constructors

    public Customer() {}

    public Customer(String nif, String name, String email, String phoneNumber, Address address) {
        this.nif = nif;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters...

    public Long getId() {
        return id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    } 

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    } 

    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    } 

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    // Utility methods to manage bidirectional relationship

    /**
     * Adds an order to the customer's set of orders.
     * Also sets this customer as the owner of the order.
     * 
     * @param order The order to be added.
     */
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

    /**
     * Removes an order from the customer's set of orders.
     * Also disassociates this customer from the order.
     * 
     * @param order The order to be removed.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }
}
