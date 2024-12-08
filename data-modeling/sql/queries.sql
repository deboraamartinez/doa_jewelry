-- 1. Add a new customer (Create)
INSERT INTO Customer (customer_id, name, email, phone, created_at)
VALUES (UUID(), 'Jane Doe', 'jane.doe@example.com', '123-456-7890', NOW());

-- 2. Retrieve all orders associated with a specific customer (Retrieve)
SELECT o.order_id, o.purchase_date, o.total_amount, c.name AS customer_name
FROM `Order` o
JOIN Customer c ON o.customer_id = c.customer_id
WHERE c.name = 'Jane Doe';

-- 3. Update information for a jewelry item (Update)
UPDATE Jewelry
SET price = price + 100.00
WHERE jewelry_id = (SELECT jewelry_id FROM Jewelry WHERE name = 'Diamond Ring');

-- 4. Delete a supplier (Delete)
DELETE FROM Supplier
WHERE supplier_id = (SELECT supplier_id FROM Supplier WHERE name = 'Gold Supplies Inc.');

-- 5. Check stock levels for jewelry items (Relational Query)
SELECT name, quantity_in_stock, category
FROM Jewelry
ORDER BY quantity_in_stock ASC;

-- 6. Insert a new order with order details (Create)
INSERT INTO `Order` (order_id, customer_id, employee_id, purchase_date, total_amount, status)
VALUES (
    UUID(),
    (SELECT customer_id FROM Customer WHERE name = 'Jane Doe'),
    (SELECT employee_id FROM Employee WHERE name = 'John Smith'),
    NOW(),
    1500.00,
    'PENDING'
);

INSERT INTO OrderDetails (order_detail_id, order_id, jewelry_id, quantity_ordered, line_total)
VALUES (
    UUID(),
    (SELECT order_id FROM `Order` WHERE customer_id = (SELECT customer_id FROM Customer WHERE name = 'Jane Doe') AND status = 'PENDING'),
    (SELECT jewelry_id FROM Jewelry WHERE name = 'Gold Necklace'),
    1,
    1500.00
);

-- 7. Retrieve payment information for a specific customer (Retrieve)
SELECT p.payment_id, p.payment_date, p.amount, p.payment_method, c.name AS customer_name
FROM Payment p
JOIN `Order` o ON p.order_id = o.order_id
JOIN Customer c ON o.customer_id = c.customer_id
WHERE c.name = 'Jane Doe';

-- 8. Retrieve salesperson performance (Relational Query)
SELECT e.name AS salesperson_name, COUNT(o.order_id) AS total_orders, SUM(o.total_amount) AS total_sales
FROM Employee e
JOIN `Order` o ON e.employee_id = o.employee_id
WHERE e.role = 'SALESPERSON'
GROUP BY e.name
ORDER BY total_sales DESC;

-- 9. Retrieve pending orders and their details (Retrieve)
SELECT o.order_id, c.name AS customer_name, o.total_amount, o.status, od.quantity_ordered, j.name AS jewelry_name
FROM `Order` o
JOIN Customer c ON o.customer_id = c.customer_id
JOIN OrderDetails od ON o.order_id = od.order_id
JOIN Jewelry j ON od.jewelry_id = j.jewelry_id
WHERE o.status = 'PENDING';

-- 10. Update the status of an order (Update)
UPDATE `Order`
SET status = 'COMPLETED', updated_at = NOW()
WHERE order_id = (SELECT order_id FROM `Order` WHERE customer_id = (SELECT customer_id FROM Customer WHERE name = 'Jane Doe') AND status = 'PENDING');

-- 11. Delete an order and its details (Delete)
DELETE FROM OrderDetails
WHERE order_id = (SELECT order_id FROM `Order` WHERE customer_id = (SELECT customer_id FROM Customer WHERE name = 'Jane Doe') AND status = 'COMPLETED');

DELETE FROM `Order`
WHERE customer_id = (SELECT customer_id FROM Customer WHERE name = 'Jane Doe') AND status = 'COMPLETED';
