package com.doa.doajewelry.controller;

import com.doa.doajewelry.dtos.OrderDTO;
import com.doa.doajewelry.entities.enums.OrderStatus;
import com.doa.doajewelry.services.OrderService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing Orders.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Creates a new order.
     * @param dto the order data.
     * @return the created OrderDTO.
     */
    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO dto) {
        return service.createOrder(dto);
    }

    /**
     * Retrieves all orders.
     * @return a list of OrderDTOs.
     */
    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return service.getAllOrders();
    }

    /**
     * Retrieves a specific order by ID.
     * @param id the order ID.
     * @return the corresponding OrderDTO.
     */
    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return service.getOrder(id);
    }

    /**
     * Retrieves all orders with a specific status.
     * @param status the order status to filter by.
     * @return a list of OrderDTOs with the specified status.
     */
    @GetMapping("/status/{status}")
    public List<OrderDTO> getAllOrdersByStatus(@PathVariable OrderStatus status) {
        return service.getAllOrdersByStatus(status);
    }

    /**
     * Updates an existing order.
     * @param id the order ID.
     * @param dto the updated order data.
     * @return the updated OrderDTO.
     */
    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderDTO dto) {
        return service.updateOrder(id, dto);
    }

    /**
     * Deletes an order by ID.
     * @param id the order ID.
     */
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
    }
}
