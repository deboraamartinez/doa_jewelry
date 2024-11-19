//package doa_jewelry.service;
//
//import doa_jewelry.entity.Payment;
//import doa_jewelry.exception.RepositoryException;
//import doa_jewelry.repository.PaymentRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public class PaymentService {
//
//    private final PaymentRepository paymentRepository = new PaymentRepository();
//
//    public Payment createPayment(Payment payment) {
//        try {
//            return paymentRepository.save(payment);
//        } catch (RepositoryException e) {
//            System.err.println("Error creating payment: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public Optional<Payment> getPaymentById(Long id) {
//        return paymentRepository.findById(id);
//    }
//
//    public List<Payment> getAllPayments() {
//        return paymentRepository.findAll();
//    }
//
//    public boolean deletePayment(Long id) {
//        try {
//            paymentRepository.deleteById(id);
//            return true;
//        } catch (RepositoryException e) {
//            System.err.println("Error deleting payment: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public Payment updatePayment(Payment payment) {
//        try {
//            return paymentRepository.update(payment);
//        } catch (RepositoryException e) {
//            System.err.println("Error updating payment: " + e.getMessage());
//            return null;
//        }
//    }
//}
