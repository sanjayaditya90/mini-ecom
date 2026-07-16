CREATE TABLE customer (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL ,
    phone_no VARCHAR(15) NOT NULL ,
    address VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    user_type TINYINT NOT NULL CHECK (user_type IN (1, 2)),
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    modified_date DATE,
    modified_by VARCHAR(50)
);

CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    product_description VARCHAR(500),
    category VARCHAR(50) NOT NULL,
    brand VARCHAR(50),
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    modified_date DATE,
    modified_by VARCHAR(50)
);


CREATE TABLE cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id VARCHAR(100) NOT NULL,
    product_id INT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    total_price DECIMAL(10,2) NOT NULL,
    reseller_id VARCHAR(100),
    reseller_name VARCHAR(255),
    added_date DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(100) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    order_status VARCHAR(20) NOT NULL,
    created_date DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    modified_date DATE,
    modified_by VARCHAR(50)
);
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);