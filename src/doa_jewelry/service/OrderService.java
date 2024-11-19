//package doa_jewelry.service;
//
//import doa_jewelry.entity.Jewelry;
//import doa_jewelry.entity.Order;
//import doa_jewelry.exception.EntityAlreadyExistsException;
//import doa_jewelry.exception.EntityNotFoundException;
//import doa_jewelry.exception.InsufficientUnitsException;
//import doa_jewelry.exception.RepositoryException;
//import doa_jewelry.repository.OrderRepository;
//
//import java.util.List;
//
//public class OrderService {
//
//    private final OrderRepository orderRepository = new OrderRepository();
//    private final JewelryService jewelryService = new JewelryService();
//
//    public Order createOrder(Order order) throws RepositoryException {
//        for (Jewelry item : order.getItems()) {
//            Jewelry jewelry = jewelryService.getJewelryById(item.getId());
//            if (jewelry == null) {
//                throw new EntityNotFoundException(Jewelry.class);
//            }
//            if (jewelry.getStockQuantity() < 1) {
//                throw new InsufficientUnitsException("Insufficient stock for jewelry ID: " + item.getId());
//            }
//            jewelryService.decreaseStock(jewelry, 1);
//        }
//        return orderRepository.save(order);
//    }
//
//    public Order getOrderById(Long id) throws EntityNotFoundException {
//        return orderRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(Order.class));
//    }
//
//    public List<Order> getAllOrders() throws RepositoryException {
//        return orderRepository.findAll();
//    }
//
//    public void deleteOrder(Long id) throws EntityNotFoundException {
//        orderRepository.deleteById(id);
//    }
//
//    public Order updateOrder(Order order) throws RepositoryException {
//        return orderRepository.update(order);
//    }
//}
