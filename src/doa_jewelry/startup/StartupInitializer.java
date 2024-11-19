package doa_jewelry.startup;

import doa_jewelry.controller.*;
import doa_jewelry.entity.*;
import doa_jewelry.entity.Jewelry.*;
import doa_jewelry.entity.Order.OrderStatus;
import doa_jewelry.entity.Payment.PaymentMethod;
import doa_jewelry.exception.*;

import java.time.LocalDate;

public class StartupInitializer {

    private final EmployeeController employeeController = new EmployeeController();
    private final CustomerController customerController = new CustomerController();
//    private final JewelryController jewelryController = new JewelryController();
//    private final OrderController orderController = new OrderController();
//    private final PaymentController paymentController = new PaymentController();

    public void initializeData() {
            // Create Employees
            Employee manager = new Manager("Alice Smith", "NIF001", LocalDate.now(), 5000, 100000);
            employeeController.createEmployee(manager);

            //Employee salesperson = new Salesperson(Long.valueOf(2), "Bob Johnson", "NI2", LocalDate.now(), 4000);
            //employeeController.createEmployee(salesperson);
            //employeeController.updateEmployee(salesperson);
            employeeController.getAllEmployees().forEach(System.out::println);

            // Create Customers
           // Customer customer = new Customer("Charlie Brown", "123445", "charlie1@example.com", "1234567890", "123 Main St");
            //customerController.createCustomer(customer);
            //customerController.getCustomerById(Long.valueOf(2));
            //customerController.deleteCustomer(Long.valueOf(2));
            //customerController.getAllCustomers().forEach(System.out::println);
//
//            // Create Jewelry
//            Jewelry ring = new Ring("Diamond Ring", MaterialType.DIAMOND, 5.0, 1500, 10, CategoryType.LUXURY, 6.5);
//            jewelryController.createJewelry(ring);
//
//            Jewelry necklace = new Necklace("Gold Necklace", MaterialType.GOLD, 10.0, 2000, 5, CategoryType.LUXURY, 18);
//            jewelryController.createJewelry(necklace);
//
//            // Create Order
//            Order order = new Order(customer, LocalDate.now());
//            order.addItem(ring);
//            order.addItem(necklace);
//            orderController.createOrder(order);
//
//            // Create Payment
//            Payment payment = new Payment(order.getTotalAmount(), LocalDate.now(), PaymentMethod.CREDIT_CARD, order);
//            paymentController.createPayment(payment);
    }
}
