package com.doa.doajewelry.controller;

import com.doa.doajewelry.dtos.CustomerDTO;
import com.doa.doajewelry.services.CustomerService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    /**
     * Creates a new customer.
     * @param dto the customer data.
     * @return the created CustomerDTO with HTTP status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<CustomerDTO> create(@Valid @RequestBody CustomerDTO dto) {
        CustomerDTO created = service.createCustomer(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Retrieves all customers.
     * @return a list of CustomerDTOs with HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll() {
        List<CustomerDTO> customers = service.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Retrieves a specific customer by ID.
     * @param id the customer ID.
     * @return the corresponding CustomerDTO with HTTP status 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getOne(@PathVariable Long id) {
        CustomerDTO dto = service.getCustomer(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Updates an existing customer.
     * @param id the customer ID.
     * @param dto the updated customer data.
     * @return the updated CustomerDTO with HTTP status 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerDTO dto) {
        CustomerDTO updated = service.updateCustomer(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Deletes a customer by ID.
     * @param id the customer ID.
     * @return HTTP status 204 (No Content) upon successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
