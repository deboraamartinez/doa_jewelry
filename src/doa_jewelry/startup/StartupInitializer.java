package doa_jewelry.startup;

import doa_jewelry.controller.*;
import doa_jewelry.entity.*;
import doa_jewelry.entity.Order.Item;
import doa_jewelry.exception.RepositoryException;
import doa_jewelry.repository.*;
import doa_jewelry.service.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * StartupInitializer initializes the system with predefined data such as employees, customers, jewelry items, orders, and payments.
 * It also demonstrates basic CRUD operations and handles potential exceptions during initialization.
 */
public class StartupInitializer {

    // Controllers to manage different entities
    private final EmployeeController employeeController;
    private final CustomerController customerController;
    private final JewelryController jewelryController;
    private final OrderController orderController;
    private final PaymentController paymentController;

    public StartupInitializer() {
        // Initialize repositories for data storage and retrieval
        EmployeeRepository employeeRepository = new EmployeeRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        JewelryRepository jewelryRepository = new JewelryRepository();
        OrderRepository orderRepository = new OrderRepository();
        PaymentRepository paymentRepository = new PaymentRepository();

        // Create services and inject their respective repositories
        PaymentService paymentService = new PaymentService(paymentRepository, orderRepository);
        JewelryService jewelryService = new JewelryService(jewelryRepository, orderRepository);
        OrderService orderService = new OrderService(orderRepository, paymentService, customerRepository, jewelryService);
        CustomerService customerService = new CustomerService(customerRepository, orderService);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // Create controllers and inject their respective services
        employeeController = new EmployeeController(employeeService);
        customerController = new CustomerController(customerService);
        jewelryController = new JewelryController(jewelryService);
        orderController = new OrderController(orderService, paymentService);
        paymentController = new PaymentController(paymentService);
    }

    /**
     * Initializes the system with sample data and demonstrates various operations.
     */
    public void initializeData() {
        try {
            // Create sample employees
            Employee manager = new Manager("Alice Smith", "NIF001", LocalDate.now().minusYears(5), 5000, 100000);
            Employee salesperson = new Salesperson("Bob Johnson", "NIF002", LocalDate.now().minusYears(2), 3000);
            employeeController.createEmployee(manager);
            employeeController.createEmployee(salesperson);

            // Create sample customers
            Customer customer1 = new Customer("John Doe", "NIF100", "john.doe@example.com", "123456789", "123 Main St");
            Customer customer2 = new Customer("Jane Roe", "NIF101", "jane.roe@example.com", "987654321", "456 Elm St");
            customerController.createCustomer(customer1);
            customerController.createCustomer(customer2);

            // Create sample jewelry items
            Jewelry necklace = new Necklace("Elegant Necklace", MaterialType.GOLD, 50.0, 1000.0, 10, JewelryCategory.LUXURY, 18.0);
            Jewelry ring = new Ring("Diamond Ring", MaterialType.DIAMOND, 5.0, 5000.0, 5, JewelryCategory.LUXURY, 7.0);
            Jewelry earring = new Earring("Silver Earring", MaterialType.SILVER, 10.0, 200.0, 20, JewelryCategory.CASUAL, "Hook");
            jewelryController.createJewelry(necklace);
            jewelryController.createJewelry(ring);
            jewelryController.createJewelry(earring);

            // Create sample orders with associated items
            Order order1 = new Order();
            order1.setCustomerId(customer1.getId());
            order1.setDate(LocalDate.now());
            order1.setItems(Arrays.asList(
                    new Item(necklace.getId(), 1), // Order 1 necklace
                    new Item(ring.getId(), 1)     // Order 1 ring
            ));
            orderController.createOrder(order1);

            Order order2 = new Order();
            order2.setCustomerId(customer2.getId());
            order2.setDate(LocalDate.now());
            order2.setItems(Arrays.asList(
                    new Item(earring.getId(), 2) // Order 2 earrings
            ));
            orderController.createOrder(order2);

            // Create payments for the orders
            Payment payment1 = new Payment(order1.getTotalAmount() / 2, LocalDate.now(), PaymentMethod.CREDIT_CARD, 1L);
            Payment payment2 = new Payment(order1.getTotalAmount() / 2, LocalDate.now().plusDays(30), PaymentMethod.CREDIT_CARD, order1.getId());
            paymentController.createPayment(payment1);
            paymentController.createPayment(payment2);

            Payment payment3 = new Payment(order2.getTotalAmount(), LocalDate.now(), PaymentMethod.CASH, order2.getId());
            paymentController.createPayment(payment3);

            // Update customer details
            customer1.setPhoneNumber("555123456");
            customerController.updateCustomer(customer1);

            // Update employee details
            ((Manager) manager).setSalesGoal(120000);
            employeeController.updateEmployee(manager);

            // Demonstrate cascading delete: Delete customer and associated orders/payments
            customerController.deleteCustomer(customer2.getId());

            // Attempt to delete a jewelry item that is part of an order (expected failure)
            try {
                jewelryController.deleteJewelry(necklace.getId());
            } catch (RepositoryException e) {
                System.err.println("Cannot delete jewelry: " + e.getMessage());
            }

            // List all remaining customers
            List<Customer> customers = customerController.getAllCustomers();
            System.out.println("Customers:");
            customers.forEach(System.out::println);

            // List all employees
            List<Employee> employees = employeeController.getAllEmployees();
            System.out.println("\nEmployees:");
            employees.forEach(System.out::println);

            // List all jewelry items
            List<Jewelry> jewelries = jewelryController.getAllJewelry();
            System.out.println("\nJewelry Items:");
            jewelries.forEach(System.out::println);

            // List all orders
            List<Order> orders = orderController.getAllOrders();
            System.out.println("\nOrders:");
            orders.forEach(System.out::println);

            // List all payments
            List<Payment> payments = paymentController.getAllPayments();
            System.out.println("\nPayments:");
            payments.forEach(System.out::println);

            // Add a shutdown hook to save all data when the program exits
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    employeeController.saveAll();
                    customerController.saveAll();
                    jewelryController.saveAll();
                    orderController.saveAll();
                    paymentController.saveAll();
                    System.out.println("Data saved successfully on program exit.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));

        } catch (RepositoryException e) {
            System.err.println("Error during data initialization: " + e.getMessage());
        }
    }
}
