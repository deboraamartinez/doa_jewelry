//package doa_jewelry.repository;
//
//import doa_jewelry.entity.Order;
//import doa_jewelry.exception.EntityAlreadyExistsException;
//import doa_jewelry.exception.EntityNotFoundException;
//import doa_jewelry.exception.RepositoryException;
//
//public class OrderRepository extends MyCrudRepository<Order> {
//
//    @Override
//    public Order save(Order order) throws EntityAlreadyExistsException {
//        if (order.getId() != null && existsById(order.getId())) {
//            throw new EntityAlreadyExistsException(Order.class);
//        }
//        return super.save(order);
//    }
//
//    @Override
//    public Order update(Order order) throws EntityNotFoundException {
//        if (!existsById(order.getId())) {
//            throw new EntityNotFoundException(Order.class);
//        }
//        return super.update(order);
//    }
//
//    @Override
//    public void deleteById(Long id) throws EntityNotFoundException {
//        if (!existsById(id)) {
//            throw new EntityNotFoundException(Order.class);
//        }
//        super.deleteById(id);
//    }
//}
