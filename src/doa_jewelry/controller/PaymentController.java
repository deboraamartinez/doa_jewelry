//package doa_jewelry.controller;
//
//import doa_jewelry.entity.Payment;
//import doa_jewelry.exception.RepositoryException;
//import doa_jewelry.service.PaymentService;
//
//import java.util.List;
//
//public class PaymentController {
//    private final PaymentService paymentService = new PaymentService();
//
//    public Payment createPayment(Payment payment) {
//        try {
//            return paymentService.createPayment(payment);
//        } catch (RepositoryException e) {
//            System.err.println("Error creating payment: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public Payment getPaymentById(Long id) {
//        try {
//            return paymentService.getPaymentById(id);
//        } catch (RepositoryException e) {
//            System.err.println("Error retrieving payment: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Payment> getAllPayments() {
//        try {
//            return paymentService.getAllPayments();
//        } catch (RepositoryException e) {
//            System.err.println("Error retrieving payments: " + e.getMessage());
//            return List.of();
//        }
//    }
//
//    public void deletePayment(Long id) {
//        try {
//            paymentService.deletePayment(id);
//        } catch (RepositoryException e) {
//            System.err.println("Error deleting payment: " + e.getMessage());
//        }
//    }
//
//    public Payment updatePayment(Payment payment) {
//        try {
//            return paymentService.updatePayment(payment);
//        } catch (RepositoryException e) {
//            System.err.println("Error updating payment: " + e.getMessage());
//            return null;
//        }
//    }
//}
