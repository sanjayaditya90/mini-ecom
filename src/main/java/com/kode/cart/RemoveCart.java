package com.kode.cart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

    public class RemoveCart {

        public void removeProduct(long productId) throws SQLException {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ecom_kode_jdbc",
                    "root",
                    "root"
            );

            String query = "DELETE FROM cart WHERE product_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, productId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product with ID " + productId + " removed from cart.");
            } else {
                System.out.println("No product found with ID " + productId + " in cart.");
            }

            ps.close();
            connection.close();
        }


}
