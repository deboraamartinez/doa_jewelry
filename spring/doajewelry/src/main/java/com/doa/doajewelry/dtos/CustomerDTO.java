package com.doa.doajewelry.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Customer.
 */
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "NIF cannot be empty")
    @Size(min = 5, max = 15, message = "NIF must be between 5 and 15 characters")
    private String nif;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    private String phoneNumber;

    @NotBlank(message = "Street cannot be empty")
    private String street;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotBlank(message = "State cannot be empty")
    private String state;

    @NotBlank(message = "ZIP Code cannot be empty")
    private String zipCode;

    @NotBlank(message = "Country cannot be empty")
    private String country;

    public CustomerDTO() {}

    // Getters and Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
}
