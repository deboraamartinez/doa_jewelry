//package doa_jewelry.repository;
//
//import doa_jewelry.entity.Payment;
//import doa_jewelry.exception.EntityAlreadyExistsException;
//import doa_jewelry.exception.EntityNotFoundException;
//import doa_jewelry.exception.RepositoryException;
//
//public class PaymentRepository extends MyCrudRepository<Payment> {
//
//    @Override
//    public Payment save(Payment payment) throws EntityAlreadyExistsException {
//        if (payment.getId() != null && existsById(payment.getId())) {
//            throw new EntityAlreadyExistsException(Payment.class);
//        }
//        Payment savedPayment = super.save(payment);
//        saveToFile();
//        return savedPayment;
//    }
//
//    @Override
//    public Payment update(Payment payment) throws EntityNotFoundException {
//        if (!existsById(payment.getId())) {
//            throw new EntityNotFoundException(Payment.class);
//        }
//        Payment updatedPayment = super.update(payment);
//        saveToFile();
//        return updatedPayment;
//    }
//
//    @Override
//    public void deleteById(Long id) throws EntityNotFoundException {
//        if (!existsById(id)) {
//            throw new EntityNotFoundException(Payment.class);
//        }
//        super.deleteById(id);
//        saveToFile();
//    }
//
//    // Implement loadFromFile() and saveToFile() methods as needed
//}
