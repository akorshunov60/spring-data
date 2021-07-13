INSERT INTO users (name) VALUES
('Sergey'),
('Nikita'),
('Daniil');

INSERT INTO orders (user_id) VALUES (1), (2), (3);

INSERT INTO products (name, price) VALUES
('Молоко 3,5%, 1 л', 89.99),
('Сметана 15%, 300 г', 75.99),
('Хлеб Бородинский, 300 г', 21.99),
('Сыр Четук 45%, 370 г', 231.99),
('Хлопья овсяные, 500 г', 63.99);

INSERT INTO order_products (order_id, product_id, quantity, last_price) VALUES
(1, 1, 1, 69.99),
(1, 2, 1, 67.99),
(1, 5, 1, 63.99),
(2, 1, 2, 89.99),
(2, 3, 2, 21.99),
(2, 4, 1, 231.99),
(3, 1, 2, 74.99),
(3, 3, 1, 20.99),
(3, 4, 1, 228.99),
(3, 5, 2, 63.99);