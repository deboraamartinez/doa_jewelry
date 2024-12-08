-- CREATE (MANAGER example)
-- 1) Create Employee with role = MANAGER
INSERT INTO Employee (name, NIF, hire_date, salary, role)
VALUES ('John Manager', 'NIF-MGR-001', '2024-01-15', 3500.00, 'MANAGER');

-- 2) Get employee_id and role from the created Employee
--    Then insert into Manager if role = MANAGER
INSERT INTO Manager (employee_id, team_sales, sales_goal)
SELECT employee_id, 0.00, 10000.00 FROM Employee WHERE NIF = 'NIF-MGR-001';

-- CREATE (SALESPERSON example)
-- 1) Create Employee with role = SALESPERSON
INSERT INTO Employee (name, NIF, hire_date, salary, role)
VALUES ('Jane Sales', 'NIF-SLS-001', '2024-01-20', 2500.00, 'SALESPERSON');

-- 2) Get employee_id and role from the created Employee
--    Then insert into Salesperson if role = SALESPERSON
INSERT INTO Salesperson (employee_id)
SELECT employee_id FROM Employee WHERE NIF = 'NIF-SLS-001';

-- READ Employee
SELECT * FROM Employee;

-- UPDATE Employee (e.g. increase salary)
UPDATE Employee
SET salary = salary + 500.00
WHERE name = 'Jane Sales';

-- DELETE Employee (First delete related Salesperson or Manager if exists)
DELETE FROM Salesperson
WHERE employee_id = (SELECT employee_id FROM Employee WHERE name = 'Jane Sales');

DELETE FROM Employee
WHERE name = 'Jane Sales';

-- Recreate Jane Sales to maintain consistency for further examples:
INSERT INTO Employee (name, NIF, hire_date, salary, role)
VALUES ('Jane Sales', 'NIF-SLS-003', '2024-02-10', 2600.00, 'SALESPERSON');

INSERT INTO Salesperson (employee_id)
SELECT employee_id FROM Employee WHERE NIF = 'NIF-SLS-003';

-- ============================================
-- MANAGER CRUD
-- (We already created a Manager: John Manager)
-- ============================================

-- READ Manager
SELECT m.*, e.name 
FROM Manager m
JOIN Employee e ON m.employee_id = e.employee_id;

-- UPDATE Manager
UPDATE Manager
SET sales_goal = sales_goal + 5000.00
WHERE employee_id = (SELECT employee_id FROM Employee WHERE name = 'John Manager');

-- DELETE Manager
DELETE FROM Manager
WHERE employee_id = (SELECT employee_id FROM Employee WHERE name = 'John Manager');

-- Also delete the Employee if desired:
DELETE FROM Employee
WHERE name = 'John Manager';

-- Recreate John Manager for future tests if needed:
INSERT INTO Employee (name, NIF, hire_date, salary, role)
VALUES ('John Manager', 'NIF-MGR-001', '2024-01-15', 3500.00, 'MANAGER');

INSERT INTO Manager (employee_id, team_sales, sales_goal)
SELECT employee_id, 0.00, 10000.00 FROM Employee WHERE NIF = 'NIF-MGR-001';

-- ============================================
-- SALESPERSON CRUD
-- ============================================
-- We have Jane Sales as a Salesperson

-- READ Salesperson
SELECT s.*, e.name
FROM Salesperson s
JOIN Employee e ON s.employee_id = e.employee_id;

-- UPDATE Salesperson (soft delete example)
UPDATE Salesperson
SET deleted_at = NOW()
WHERE employee_id = (SELECT employee_id FROM Employee WHERE name = 'Jane Sales');

-- DELETE Salesperson
DELETE FROM Salesperson
WHERE employee_id = (SELECT employee_id FROM Employee WHERE name = 'Jane Sales');

-- And then delete the Employee if you want:
DELETE FROM Employee
WHERE name = 'Jane Sales';

-- Recreate Jane Sales for consistency:
INSERT INTO Employee (name, NIF, hire_date, salary, role)
VALUES ('Jane Sales', 'NIF-SLS-003', '2024-02-10', 2600.00, 'SALESPERSON');

INSERT INTO Salesperson (employee_id)
SELECT employee_id FROM Employee WHERE NIF = 'NIF-SLS-003';

-- ============================================
-- CUSTOMER CRUD
-- ============================================

-- CREATE Customer
INSERT INTO Customer (name, NIF, email, phone_number, address)
VALUES ('Client X', 'NIFCUST01', 'clientx@example.com', '123-456-7890', 'Address 123');

-- READ Customer
SELECT * FROM Customer;

-- UPDATE Customer
UPDATE Customer
SET address = 'Address 456, District Y'
WHERE name = 'Client X';

-- DELETE Customer
DELETE FROM Customer
WHERE name = 'Client X';

-- Recreate Customer
INSERT INTO Customer (name, NIF, email, phone_number, address)
VALUES ('Client X', 'NIFCUST01', 'clientx@example.com', '123-456-7890', 'Address 123');

-- ============================================
-- SUPPLIER CRUD
-- ============================================

-- CREATE Supplier
INSERT INTO Supplier (name, VAT_number, phone_number, address)
VALUES ('Gold Supplier Inc.', 'VAT123', '555-111-2222', 'Ouro Street, 10');

-- READ Supplier
SELECT * FROM Supplier;

-- UPDATE Supplier
UPDATE Supplier
SET address = 'Main Avenue, 999'
WHERE name = 'Gold Supplier Inc.';

-- DELETE Supplier
DELETE FROM Supplier
WHERE name = 'Gold Supplier Inc.';

-- Recreate Supplier
INSERT INTO Supplier (name, VAT_number, phone_number, address)
VALUES ('Gold Supplier Inc.', 'VAT123', '555-111-2222', 'Ouro Street, 10');

-- ============================================
-- JEWELRY CRUD
-- ============================================

-- CREATE Jewelry
INSERT INTO Jewelry (name, type, material, weight, price, quantity_in_stock, category)
VALUES ('Diamond Ring', 'RING', 'Gold', 5.00, 2000.00, 10, 'LUXURY');

-- READ Jewelry
SELECT * FROM Jewelry;

-- UPDATE Jewelry
UPDATE Jewelry
SET price = price + 100.00
WHERE name = 'Diamond Ring';

-- DELETE Jewelry
DELETE FROM Jewelry
WHERE name = 'Diamond Ring';

-- Recreate Jewelry
INSERT INTO Jewelry (name, type, material, weight, price, quantity_in_stock, category)
VALUES ('Diamond Ring', 'RING', 'Gold', 5.00, 2000.00, 10, 'LUXURY');

-- ============================================
-- SUPPLIERPROVIDESJEWELRY CRUD
-- ============================================

-- CREATE relation (ensure we have Supplier and Jewelry)
-- Assuming we already have 'Gold Supplier Inc.' and 'Diamond Ring' created above
INSERT INTO SupplierProvidesJewelry (supplier_id, jewelry_id, supplied_quantity)
SELECT s.supplier_id, j.jewelry_id, 100
FROM Supplier s, Jewelry j
WHERE s.name = 'Gold Supplier Inc.' AND j.name = 'Diamond Ring';

-- READ SupplierProvidesJewelry
SELECT spj.*, s.name AS supplier_name, j.name AS jewelry_name
FROM SupplierProvidesJewelry spj
JOIN Supplier s ON spj.supplier_id = s.supplier_id
JOIN Jewelry j ON spj.jewelry_id = j.jewelry_id;

-- UPDATE SupplierProvidesJewelry
UPDATE SupplierProvidesJewelry
SET supplied_quantity = supplied_quantity + 50
WHERE supplier_id = (SELECT supplier_id FROM Supplier WHERE name = 'Gold Supplier Inc.')
  AND jewelry_id = (SELECT jewelry_id FROM Jewelry WHERE name = 'Diamond Ring');

-- DELETE SupplierProvidesJewelry
DELETE FROM SupplierProvidesJewelry
WHERE supplier_id = (SELECT supplier_id FROM Supplier WHERE name = 'Gold Supplier Inc.')
  AND jewelry_id = (SELECT jewelry_id FROM Jewelry WHERE name = 'Diamond Ring');

-- Recreate SupplierProvidesJewelry if needed:
INSERT INTO SupplierProvidesJewelry (supplier_id, jewelry_id, supplied_quantity)
SELECT s.supplier_id, j.jewelry_id, 100
FROM Supplier s, Jewelry j
WHERE s.name = 'Gold Supplier Inc.' AND j.name = 'Diamond Ring';

-- ============================================
-- ORDER CRUD
-- ============================================
-- Need an existing Customer and Employee (SALESPERSON)

-- Customer 'Client X' and Salesperson 'Jane Sales' already exist

-- CREATE Order
INSERT INTO `Order` (purchase_date, customer_id, employee_id, total_amount, status)
SELECT '2024-03-01', c.customer_id, e.employee_id, 2000.00, 'PENDING'
FROM Customer c, Employee e
WHERE c.name = 'Client X'
  AND e.name = 'Jane Sales';

-- READ Order
SELECT o.*, c.name AS customer_name, e.name AS employee_name
FROM `Order` o
JOIN Customer c ON o.customer_id = c.customer_id
JOIN Employee e ON o.employee_id = e.employee_id;

-- UPDATE Order
UPDATE `Order`
SET status = 'PAID'
WHERE order_id = (
    SELECT * FROM (
        SELECT order_id FROM `Order`
        WHERE total_amount = 2000.00 AND status = 'PENDING'
    ) AS temp
);

-- DELETE Order
DELETE FROM `Order`
WHERE order_id = (
    SELECT * FROM (
        SELECT order_id FROM `Order`
        WHERE total_amount = 2000.00 AND status = 'PAID'
    ) AS temp
);

-- Recreate Order if needed:
INSERT INTO `Order` (purchase_date, customer_id, employee_id, total_amount, status)
SELECT '2024-03-01', c.customer_id, e.employee_id, 2000.00, 'PENDING'
FROM Customer c, Employee e
WHERE c.name = 'Client X'
  AND e.name = 'Jane Sales';

-- ============================================
-- ORDERDETAILS CRUD
-- ============================================
-- We have a PENDING Order and 'Diamond Ring' Jewelry

-- CREATE OrderDetails
INSERT INTO OrderDetails (order_id, jewelry_id, quantity_ordered, line_total)
SELECT o.order_id, j.jewelry_id, 1, j.price
FROM `Order` o, Jewelry j
WHERE o.status = 'PENDING'
  AND o.total_amount = 2000.00
  AND j.name = 'Diamond Ring';

-- READ OrderDetails
SELECT od.*, j.name AS jewelry_name
FROM OrderDetails od
JOIN Jewelry j ON od.jewelry_id = j.jewelry_id;

-- UPDATE OrderDetails (e.g. change quantity)
UPDATE OrderDetails
SET quantity_ordered = 2, line_total = line_total * 2
WHERE order_id = (
    SELECT * FROM (
        SELECT order_id FROM `Order`
        WHERE status = 'PENDING' AND total_amount = 2000.00
    ) AS temp
)
AND jewelry_id = (SELECT jewelry_id FROM Jewelry WHERE name = 'Diamond Ring');

-- DELETE OrderDetails
DELETE FROM OrderDetails
WHERE order_id = (
    SELECT * FROM (
        SELECT order_id FROM `Order`
        WHERE status = 'PENDING' AND total_amount = 2000.00
    ) AS temp
)
AND jewelry_id = (SELECT jewelry_id FROM Jewelry WHERE name = 'Diamond Ring');

-- Recreate OrderDetails if needed:
INSERT INTO OrderDetails (order_id, jewelry_id, quantity_ordered, line_total)
SELECT o.order_id, j.jewelry_id, 1, j.price
FROM `Order` o, Jewelry j
WHERE o.status = 'PENDING'
  AND o.total_amount = 2000.00
  AND j.name = 'Diamond Ring';

-- ============================================
-- PAYMENT CRUD
-- ============================================
-- We have a PENDING Order of 2000.00

-- CREATE Payment
INSERT INTO Payment (order_id, payment_date, amount, payment_method)
SELECT o.order_id, '2024-03-02', 2000.00, 'CREDIT_CARD'
FROM `Order` o
WHERE o.status = 'PENDING' AND o.total_amount = 2000.00;

-- READ Payment
SELECT p.*, o.total_amount
FROM Payment p
JOIN `Order` o ON p.order_id = o.order_id;

-- UPDATE Payment (change payment method)
UPDATE Payment
SET payment_method = 'BANK_TRANSFER'
WHERE payment_id = (
    SELECT * FROM (
        SELECT payment_id FROM Payment
        WHERE amount = 2000.00 AND payment_method = 'CREDIT_CARD'
    ) AS temp
);

-- DELETE Payment
DELETE FROM Payment
WHERE payment_id = (
    SELECT * FROM (
        SELECT payment_id FROM Payment
        WHERE amount = 2000.00 AND payment_method = 'BANK_TRANSFER'
    ) AS temp
);
