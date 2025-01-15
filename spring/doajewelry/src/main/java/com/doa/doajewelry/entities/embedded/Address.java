package com.doa.doajewelry.entities.embedded;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

/**
 * Embeddable class representing an Address.
 * This class is used to embed address-related fields into other entities.
 */
@Embeddable
public class Address {

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

    // Constructors

    public Address() {}

    public Address(String street, String city, String state, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    // Getters and Setters...

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
