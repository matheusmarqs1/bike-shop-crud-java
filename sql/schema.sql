-- Represents the store's customers
CREATE TABLE `customers` (
    `id` INT UNSIGNED AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `telephone` VARCHAR(25) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`id`)
);

-- Represents the store's products
CREATE TABLE `products` (
    `id` INT UNSIGNED AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `description` TEXT NOT NULL,
    `category` ENUM('bicycle', 'bicycle components', 'accessories') NOT NULL,
    `price` DECIMAL(7,2) NOT NULL CHECK(`price` > 0.0),
    `inventory` SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY(`id`)
);

-- Represents the order made by the client
CREATE TABLE `orders` (
    `id` INT UNSIGNED AUTO_INCREMENT,
    `customer_id` INT UNSIGNED,
    `order_number` VARCHAR(20) NOT NULL UNIQUE,
    `total_amount` DECIMAL(7,2) UNSIGNED NOT NULL DEFAULT 0.0,
    `status` ENUM('pending', 'paid', 'shipped', 'delivered', 'canceled') NOT NULL DEFAULT 'pending',
    `datetime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`customer_id`) REFERENCES `customers`(`id`) ON DELETE CASCADE
);

-- Represents the items requested by the customer and which make up the order
CREATE TABLE `order_items` (
    `id` INT UNSIGNED AUTO_INCREMENT,
    `product_id` INT UNSIGNED,
    `order_id` INT UNSIGNED,
    `quantity` SMALLINT NOT NULL CHECK(`quantity` > 0),
    `unit_price` DECIMAL(7,2) UNSIGNED NOT NULL DEFAULT 0.0,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`product_id`) REFERENCES `products`(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE,
    UNIQUE(`order_id`, `product_id`)
);

-- VIEWS

-- Presents the products available in stock
CREATE VIEW `products_available_in_stock` AS
SELECT `name`, `description`, `category`, `price`, `inventory`
FROM `products`
WHERE `inventory` > 0;

-- Presents the products that are out of stock
CREATE VIEW `out_of_stock_products` AS
SELECT `name`,`description`, `category`, `price`, `inventory`
FROM `products`
WHERE `inventory` = 0;

-- Presents order information that will be consulted according to the status
CREATE VIEW `order_with_status` AS
SELECT `order_number`, `status`, `total_amount`, `datetime`, `first_name`, `last_name`, `telephone`
FROM `orders`
JOIN `customers` ON `customers`.`id` = `orders`.`customer_id`;

-- Presents details of each order made
CREATE VIEW `order_details` AS
SELECT `order_number`, `status`, `datetime`, `first_name`, `last_name`, `email`, `telephone`, `products`.`name`, `unit_price`, `quantity`
FROM `orders`
JOIN `customers` ON `customers`.`id` = `orders`.`customer_id`
JOIN `order_items` ON `orders`.`id` = `order_items`.`order_id`
JOIN `products` ON `products`.`id` = `order_items`.`product_id`;

-- Presents a summary of customer purchases (only orders that are not marked as pending or canceled are considered)
CREATE VIEW `customer_purchase_summary` AS
SELECT `first_name`, `last_name`,`telephone`, `email`, COUNT(*) AS "total_orders", SUM(`total_amount`) AS "total_spent"
FROM `customers`
JOIN `orders` ON `customers`.`id` = `orders`.`customer_id`
WHERE `status` NOT IN ('pending', 'canceled')
GROUP BY `customers`.`id`
ORDER BY `total_spent` DESC, `total_orders` DESC, `first_name`;

-- Presents a list of the sales volume of each product (only orders that are not marked as pending or canceled are considered)
CREATE VIEW `products_by_sales_volume` AS
SELECT `name`, `description`, `category`, SUM(`quantity`) AS "total_sold"
FROM `products`
JOIN `order_items` ON `products`.`id` = `order_items`.`product_id`
JOIN `orders` ON `order_items`.`order_id` = `orders`.`id`
WHERE `status` NOT IN ('pending', 'canceled')
GROUP BY `products`.`id`
ORDER BY `total_sold` DESC;

-- Presents a summary of sales by date (we only include orders that are not marked as pending or canceled)
CREATE VIEW `sales_by_date` AS
SELECT DATE(`datetime`) AS "order_date", COUNT(*) AS "total_orders", SUM(`total_amount`) AS "total_revenue"
FROM `orders`
WHERE `status` NOT IN ('pending', 'canceled')
GROUP BY DATE(`datetime`)
ORDER BY `order_date` DESC;


-- TRIGGERS

-- Triggered whenever an order item is inserted, calculating the total amount
CREATE TRIGGER `update_total_amount_after_insert`
AFTER INSERT ON `order_items`
FOR EACH ROW
BEGIN
    UPDATE `orders` SET `total_amount` = (
        SELECT SUM(`quantity` * `unit_price`)
        FROM `order_items`
        WHERE `order_id` = NEW.`order_id`
    )
    WHERE `id` = NEW.`order_id`;
END;

-- Triggered whenever an order item is updated, calculating the total amount
CREATE TRIGGER `update_total_amount_after_update`
AFTER UPDATE ON `order_items`
FOR EACH ROW
BEGIN
    UPDATE `orders` SET `total_amount` = (
        SELECT SUM(`quantity` * `unit_price`)
        FROM `order_items`
        WHERE `order_id` = NEW.`order_id`
    )
    WHERE `id` = NEW.`order_id`;
END;

-- Triggered whenever an order item is deleted, calculating the total value
CREATE TRIGGER `update_total_amount_after_delete`
AFTER DELETE ON `order_items`
FOR EACH ROW
BEGIN
    UPDATE `orders` SET `total_amount` = IFNULL((
        SELECT SUM(`quantity` * `unit_price`)
        FROM `order_items`
        WHERE `order_id` = OLD.`order_id`
    ), 0.0)
    WHERE `id` = OLD.`order_id`;
END;

-- Triggered whenever an order item is updated, validating whether the quantity informed is available in stock
CREATE TRIGGER `prevent_insufficient_stock_before_update`
BEFORE UPDATE ON `order_items`
FOR EACH ROW
BEGIN
    DECLARE available_stock INT;
    SELECT `inventory` INTO available_stock FROM `products` WHERE `id` = NEW.`product_id`;
    IF available_stock < NEW.`quantity` THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient stock to update the order item';
    END IF;
END;

-- Triggered whenever an order item is inserted, validating whether the quantity informed is available in stock
CREATE TRIGGER `prevent_insufficient_stock_before_insert`
BEFORE INSERT ON `order_items`
FOR EACH ROW
BEGIN
    DECLARE available_stock INT;
    SELECT `inventory` INTO available_stock FROM `products` WHERE `id` = NEW.`product_id`;
   IF available_stock < NEW.`quantity` THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient stock to add the item to the order';
    END IF;
END;

-- Triggered whenever an attempt is made to insert items into an order that is not pending
CREATE TRIGGER `prevent_modifications_to_non_pending_orders_before_insert`
BEFORE INSERT ON `order_items`
FOR EACH ROW
BEGIN
    DECLARE order_status VARCHAR(20);
    SELECT `status` INTO order_status FROM `orders` WHERE `id` = NEW.`order_id`;
    IF order_status != 'pending' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot insert products into an order that is not pending';
    END IF;
END;

-- Triggered whenever there is an attempt to update items on an order that is not pending
CREATE TRIGGER `prevent_modifications_to_non_pending_orders_before_update`
BEFORE UPDATE ON `order_items`
FOR EACH ROW
BEGIN
    DECLARE order_status VARCHAR(20);
    SELECT `status` INTO order_status FROM `orders` WHERE `id` = NEW.`order_id`;
    IF order_status != 'pending' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot update products unless order is pending';
    END IF;
END;

-- Triggered whenever there is an attempt to delete items on an order that is not pending
CREATE TRIGGER `prevent_modifications_to_non_pending_orders_before_delete`
BEFORE DELETE ON `order_items`
FOR EACH ROW
BEGIN
    DECLARE order_status VARCHAR(20);
    SELECT `status` INTO order_status FROM `orders` WHERE `id` = OLD.`order_id`;
    IF order_status != 'pending' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'It is not possible to delete products from a non-pending order';
    END IF;
END;

-- Triggered whenever an item isn the order is inserted, calculating the unit price based on the price column in the product table
CREATE TRIGGER `set_unit_price_before_insert`
BEFORE INSERT ON `order_items`
FOR EACH ROW
BEGIN
    DECLARE product_price DECIMAL(7,2);
    SELECT `price` INTO product_price FROM `products` WHERE `id` = NEW.`product_id`;
    SET NEW.`unit_price` = product_price;   
END;

-- Triggered whenever an item in the order is updated, calculating the unit price based on the price column in the product table
CREATE TRIGGER `set_unit_price_before_update`
BEFORE UPDATE ON `order_items`
FOR EACH ROW
BEGIN
    DECLARE product_price DECIMAL(7,2);
    
    IF NEW.`product_id` != OLD.`product_id` THEN
        SELECT `price` INTO product_price FROM `products`WHERE `id` = NEW.`product_id`;
        SET NEW.`unit_price` = product_price;
    END IF;
END;

-- Triggered whenever an order is marked as paid, deducting the quantity of items from stock
CREATE TRIGGER `update_inventory_after_order_paid`
AFTER UPDATE ON `orders`
FOR EACH ROW
BEGIN
    IF NEW.`status` = 'paid' AND OLD.`status` != 'paid' THEN
        UPDATE `products` p 
        JOIN `order_items` oi ON oi.`product_id` = p.`id`
        SET p.`inventory` = p.`inventory` - oi.`quantity`
        WHERE oi.`order_id` = NEW.`id`;
    END IF;
END;

--Triggered whenever an order is marked as canceled, restoring the quantity of items in stock
CREATE TRIGGER `restore_inventory_after_order_canceled`
AFTER UPDATE ON `orders`
FOR EACH ROW
BEGIN
    IF NEW.`status` = 'canceled' AND OLD.`status` != 'canceled' THEN
        UPDATE `products` p 
        JOIN `order_items` oi ON oi.`product_id` = p.`id`
        SET p.`inventory` = p.`inventory` + oi.`quantity`
        WHERE `oi`.`order_id` = NEW.`id`;
    END IF;
END;

-- Triggered whenever an attempt is made to create an order that is not pending
-- (by default, all orders in the system must be registered as pending and then updated)

CREATE TRIGGER `enforce_pending_status_before_order_creation`
BEFORE INSERT ON `orders`
FOR EACH ROW
BEGIN
    IF NEW.`status` != 'pending' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Orders can only be created with pending status';
    END IF;
END;

-- INDEXES

CREATE INDEX `product_inventory_search` ON `products`(`inventory`);
CREATE INDEX `order_status_search` ON `orders`(`status`);
CREATE INDEX `order_datetime_search` ON `orders`(`datetime`);

