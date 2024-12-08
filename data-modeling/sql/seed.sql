USE DoaJewelry;

-- Insert Employee
INSERT INTO Employee (employee_id, name, NIF, hire_date, salary, role)
VALUES 
(UUID(), 'Alice Johnson', '123456789', '2020-01-15', 5000.00, 'MANAGER'),
(UUID(), 'Bob Smith', '987654321', '2021-03-20', 3000.00, 'SALESPERSON');

-- Insert Manager
INSERT INTO Manager (employee_id, team_sales, sales_goal)
VALUES 
((SELECT employee_id FROM Employee WHERE name = 'Alice Johnson'), 100000.00, 200000.00);

-- Insert Salesperson
INSERT INTO Salesperson (employee_id)
VALUES 
((SELECT employee_id FROM Employee WHERE name = 'Bob Smith'));

-- Insert Customer
INSERT INTO Customer (customer_id, name, NIF, email, phone_number, address)
VALUES 
(UUID(), 'John Doe', '987654321', 'johndoe@example.com', '555-1234', '123 Elm St'),
(UUID(), 'Jane Smith', '123456789', 'janesmith@example.com', '555-5678', '456 Oak St');

-- Insert Supplier
INSERT INTO Supplier (supplier_id, name, VAT_number, phone_number, address)
VALUES 
(UUID(), 'GoldMasters Inc.', '111222333', '555-1111', '789 Golden St'),
(UUID(), 'SilverWorks Ltd.', '444555666', '555-2222', '101 Silver Ave');

-- Insert Jewelry
INSERT INTO Jewelry (jewelry_id, name, type, material, weight, price, quantity_in_stock, length, clasp_type, ring_size, category)
VALUES 
(UUID(), 'Golden Necklace', 'NECKLACE', 'gold', 50.00, 999.99, 10, 45.0, NULL, NULL, 'LUXURY'),
(UUID(), 'Silver Earrings', 'EARRING', 'silver', 10.00, 199.99, 20, NULL, 'LEVERBACK', NULL, 'CASUAL');

-- Insert SupplierProvidesJewelry
INSERT INTO SupplierProvidesJewelry (supplier_id, jewelry_id, supplied_quantity)
VALUES 
((SELECT supplier_id FROM Supplier WHERE name = 'GoldMasters Inc.'), 
 (SELECT jewelry_id FROM Jewelry WHERE name = 'Golden Necklace'), 100);

-- Insert Order
INSERT INTO `Order` (order_id, purchase_date, customer_id, employee_id, total_amount, status)
VALUES 
(UUID(), '2024-12-01', 
 (SELECT customer_id FROM Customer WHERE name = 'John Doe'), 
 (SELECT employee_id FROM Employee WHERE name = 'Alice Johnson'), 
 1999.98, 'PENDING');

-- Insert OrderDetails
INSERT INTO OrderDetails (order_id, jewelry_id, quantity_ordered, line_total)
VALUES 
((SELECT order_id FROM `Order` WHERE customer_id = 
 (SELECT customer_id FROM Customer WHERE name = 'John Doe')), 
 (SELECT jewelry_id FROM Jewelry WHERE name = 'Golden Necklace'), 
 2, 1999.98);

-- Insert Payment
INSERT INTO Payment (payment_id, order_id, payment_date, amount, payment_method)
VALUES 
(UUID(), 
 (SELECT order_id FROM `Order` WHERE customer_id = 
 (SELECT customer_id FROM Customer WHERE name = 'John Doe')), 
 '2024-12-02', 1999.98, 'CREDIT_CARD');
