package com.kode.cart;

import com.kode.Porduct.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cart {

    private Long productId;
    private String product;
//    int productPrice;
    private int quantity;
//    private double subTotal;

    public Cart(long id, String name, int quantity) {
        this.productId = (long) id;
        this.product = name;
        this.quantity = quantity;
    }

    public void addToCart (Long productId, String product, int quantity) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecom_kode_jdbc",
                "root",
                "root"
        );

        connection.setAutoCommit(false);

        String query = "Insert into cart(product_id, product_name, quantity) values (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);

        ps.setLong(1, productId);   // product_id
        ps.setString(2, product);   // product_name
        ps.setInt(3, quantity);

        ps.executeUpdate();         // run the insert
        connection.commit();        // save changes
        ps.close();
        connection.close();

        System.out.println("Item added to your cart.");


    }

}
