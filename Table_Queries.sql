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