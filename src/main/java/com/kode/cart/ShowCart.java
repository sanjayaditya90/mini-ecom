package com.kode.cart;

import java.sql.*;

public class ShowCart {

    public void showCart() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecom_kode_jdbc",
                "root",
                "root"
        );

        String query = "SELECT cart.product_id, cart.product_name, cart.quantity, products.price " +
                "FROM cart " +
                "JOIN products ON cart.product_id = products.id";  // assuming products table is named 'products'

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        double total = 0;

        System.out.println("+----+-------------------------+----------+----------+");
        System.out.println("| ID | Product Name            | Quantity | SubTotal |");
        System.out.println("+----+-------------------------+----------+----------+");

        while (rs.next()) {
            long id = rs.getLong("product_id");
            String name = rs.getString("product_name");
            int qty = rs.getInt("quantity");
            int price = rs.getInt("price");

            double subTotal = qty * price;
            total += subTotal;

            System.out.printf("| %-2d | %-23s | %-8d | %-8.2f |\n", id, name, qty, subTotal);
        }

        System.out.println("+----+-------------------------+----------+----------+");
        System.out.println("Total Cart Value: " + total);
        if (!rs.next()) {
            System.out.println("No items in cart.");
        }

        rs.close();
        ps.close();
        connection.close();
    }

}
