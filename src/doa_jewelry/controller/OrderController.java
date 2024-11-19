//package doa_jewelry.controller;
//
//import doa_jewelry.entity.Order;
//import doa_jewelry.exception.RepositoryException;
//import doa_jewelry.service.OrderService;
//
//import java.util.List;
//
//public class OrderController {
//
//    private final OrderService orderService = new OrderService();
//
//    public Order createOrder(Order order) {
//        try {
//            return orderService.createOrder(order);
//        } catch (RepositoryException e) {
//            System.err.println("Error creating order: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public Order getOrderById(Long id) {
//        try {
//            return orderService.getOrderById(id);
//        } catch (RepositoryException e) {
//            System.err.println("Error retrieving order: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Order> getAllOrders() {
//        try {
//            return orderService.getAllOrders();
//        } catch (RepositoryException e) {
//            System.err.println("Error retrieving orders: " + e.getMessage());
//            return List.of();
//        }
//    }
//
//    public void deleteOrder(Long id) {
//        try {
//            orderService.deleteOrder(id);
//        } catch (RepositoryException e) {
//            System.err.println("Error deleting order: " + e.getMessage());
//        }
//    }
//
//    public Order updateOrder(Order order) {
//        try {
//            return orderService.updateOrder(order);
//        } catch (RepositoryException e) {
//            System.err.println("Error updating order: " + e.getMessage());
//            return null;
//        }
//    }
//}
