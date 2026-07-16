package com.ecom.cart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRepo {
	public PreparedStatement ps;
	public Connection connection;

	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/e_commerce", "root",
					"strong@15104961");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void addItem(Cart cart) throws SQLException {

		connection.setAutoCommit(false);

		String query = "INSERT INTO cart "
				+ "(customer_id, product_id, product_name, product_price, quantity, total_price, reseller_id, reseller_name) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = connection.prepareStatement(query)) {

			double totalPrice = (cart.getProductPrice() * cart.getQuantity());
			ps.setString(1, cart.getCustomerId());
			ps.setInt(2, cart.getProductId());
			ps.setString(3, cart.getProductName());
			ps.setDouble(4, cart.getProductPrice());
			ps.setInt(5, cart.getQuantity());
			ps.setDouble(6, totalPrice);
			ps.setString(7, cart.getResellerId());
			ps.setString(8, cart.getResellerName());

			ps.executeUpdate();
			connection.commit();

			ps.close();

			System.out.println("Item added to your cart.");
		}
	}

	public void removeItem(int productId, String customerId) throws SQLException {

		String query = "DELETE FROM cart WHERE product_id = ? and customer_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, productId);
			ps.setString(2, customerId);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Product with ID " + productId + " removed from cart.");
			} else {
				System.out.println("No product found with ID " + productId + " in cart.");
			}
		}

	}

	public void getcartItems(String customerId) throws SQLException {
		String query = "SELECT * FROM cart WHERE customer_id = ?"; // assuming products table is named 'products'

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, customerId);
		ResultSet rs = ps.executeQuery();

		double total = 0;

		System.out.println("+----+-------------------------+----------+------------+------------+");
		System.out.println("| ID | Product Name            | Quantity | Price      | Sub Total  |");
		System.out.println("+----+-------------------------+----------+------------+------------+");

		if (rs == null) {
			System.out.println("No items in cart.");
		}

		while (rs.next()) {
			long id = rs.getLong("product_id");
			String name = rs.getString("product_name");
			int qty = rs.getInt("quantity");
			double price = rs.getDouble("product_price");

			double subTotal = rs.getDouble("total_price");
			total += subTotal;

			System.out.printf("| %-2d | %-23s | %-8d | %-10.2f | %-10.2f |\n", id, name, qty, price, subTotal);
		}

		System.out.println("+----+-------------------------+----------+------------+------------+");
		System.out.printf("Total Cart Value: %.2f%n", total);

		rs.close();
		ps.close();
	}
}
