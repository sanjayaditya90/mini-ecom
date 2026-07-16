package com.ecom.order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {

	public PreparedStatement ps;
	public Connection connection;

	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/e_commerce",
					"root",
					"strong@15104961");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public int saveOrder(Order order)//save order
	        throws FailedToCreateOrderException {

	    String sql = "INSERT INTO orders (customer_id,total_amount,order_status,created_date,created_by,modified_date,modified_by) VALUES (?,?,?,?,?,?,?)";

	    try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

	        ps.setString(1, order.getUserId());
	        ps.setDouble(2, order.getTotalAmount());
	        ps.setString(3, order.getOrderStatus());
	        ps.setDate(4, java.sql.Date.valueOf(order.getCreatedDate()));
	        ps.setString(5, order.getCreatedBy());
	        ps.setDate(6, java.sql.Date.valueOf(order.getModifiedDate()));
	        ps.setString(7, order.getModifiedBy());

	        int rows = ps.executeUpdate();

	        if (rows == 0) {
	            throw new FailedToCreateOrderException("Failed to create order.");
	        }

	        ResultSet rs = ps.getGeneratedKeys();

	        if (rs.next()) {
	            return rs.getInt(1);
	        }

	        return 0;

	    } catch (SQLException e) {

	        throw new FailedToCreateOrderException("Failed to create order.", e);

	    }

	}
	public boolean saveOrderItem(OrderItem item)//2
	        throws FailedToCreateOrderException {

	    String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?,?,?,?)";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setInt(1, item.getOrderId());
	        ps.setInt(2, item.getProductId());
	        ps.setInt(3, item.getQuantity());
	        ps.setDouble(4, item.getPrice());

	        int rows = ps.executeUpdate();

	        if (rows == 0) {
	            throw new FailedToCreateOrderException("Failed to save order item.");
	        }

	        return true;

	    } catch (SQLException e) {

	        throw new FailedToCreateOrderException("Failed to save order item.", e);

	    }
	}
	public boolean verifyStock(int productId, int quantity)//3
	        throws InsufficientStockException {

	    String sql = "SELECT quantity FROM product WHERE product_id=?";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setInt(1, productId);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            int availableStock = rs.getInt("quantity");

	            if (availableStock >= quantity) {
	                return true;
	            } else {
	                throw new InsufficientStockException(
	                        "Only " + availableStock + " items available in stock.");
	            }

	        } else {

	            throw new InsufficientStockException("Product not found.");

	        }

	    } catch (SQLException e) {

	        throw new InsufficientStockException("Unable to verify stock.", e);

	    }

	}
	public boolean reduceStock(int productId, int quantity)//4
	        throws FailedToCreateOrderException {

	    String sql = "UPDATE product SET quantity = quantity - ? WHERE product_id = ?";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setInt(1, quantity);
	        ps.setInt(2, productId);

	        int rows = ps.executeUpdate();

	        if (rows == 0) {
	            throw new FailedToCreateOrderException("Failed to update product stock.");
	        }

	        return true;

	    } catch (SQLException e) {

	        throw new FailedToCreateOrderException("Failed to reduce product stock.", e);

	    }

	}
	public void clearCart(String customerId)//5
	        throws SQLException {

	    String sql = "DELETE FROM cart WHERE customer_id = ?";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setString(1, customerId);

	        ps.executeUpdate();

	    }
	}
	public void showMyOrders(String customerId)//6
	        throws SQLException {

	    String sql = "SELECT * FROM orders WHERE customer_id=?";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setString(1, customerId);

	        ResultSet rs = ps.executeQuery();

	        System.out.println("--------------------------------------------------------------");
	        System.out.printf("%-10s %-12s %-15s %-15s%n",
	                "Order Id",
	                "Amount",
	                "Status",
	                "Created Date");
	        System.out.println("--------------------------------------------------------------");

	        while (rs.next()) {

	            System.out.printf("%-10d %-12.2f %-15s %-15s%n",
	                    rs.getInt("order_id"),
	                    rs.getDouble("total_amount"),
	                    rs.getString("order_status"),
	                    rs.getDate("created_date"));
	        }

	    }
	}
	public boolean cancelOrder(int orderId)
	        throws FailedToCreateOrderException {

	    String sql =
	            "UPDATE orders SET order_status=?,modified_date=? WHERE order_id=?";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setString(1, OrderStatus.CANCELLED);
	        ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
	        ps.setInt(3, orderId);

	        int rows = ps.executeUpdate();

	        if (rows == 0) {
	            throw new FailedToCreateOrderException("Order not found.");
	        }

	        return true;

	    } catch (SQLException e) {

	        throw new FailedToCreateOrderException(
	                "Failed to cancel order.", e);

	    }
	}
	

}