package com.ecom.product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.ecom.Util.DBConnection;
import com.ecom.app.AppSession;
import com.ecom.user.User;

public class ProductRepo {
	public PreparedStatement ps;
	public Connection connection;

	public void saveProduct(AppSession appSession, String filePath) throws Exception {
		String sql = " INSERT INTO product (product_name, product_description, category, brand, price, quantity, created_date, created_by, modified_date, modified_by) VALUES(?,?,?,?,?,?,?,?,?,?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String line;
			String header = br.readLine();

			if (header == null) {
				throw new ProductUploadException("CSV file is empty.");
			}

			int count = 0;

			while ((line = br.readLine()) != null) {

				String[] data = line.split(",");

				if (data.length != 6) {
					continue;
				}

				ps.setString(1, data[0]);
				ps.setString(2, data[1]);
				ps.setString(3, data[2]);
				ps.setString(4, data[3]);
				ps.setDouble(5, Double.parseDouble(data[4]));
				ps.setInt(6, Integer.parseInt(data[5]));
				ps.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
				ps.setString(8, appSession.getLoggedInUser().getCreatedBy());
				ps.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
				ps.setString(10, appSession.getLoggedInUser().getCreatedBy());

				ps.addBatch();
				count++;
			}

			ps.executeBatch();

			System.out.println(count + " Products Uploaded Successfully.");

			ps.executeBatch();
		} catch (ProductUploadException e) {
			System.out.println("CSV file not found.");
		} catch (IOException e) {
			throw new ProductUploadException("Failed to read CSV file.", e);
		}
	}

	public boolean saveProduct(Product product) throws FailedToAddProductException {

		String sql = " INSERT INTO product (product_name, product_description, category, brand, price, quantity, created_date, created_by, modified_date, modified_by) VALUES(?,?,?,?,?,?,?,?,?,?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, product.getProductName());
			ps.setString(2, product.getProductDescription());
			ps.setString(3, product.getCategory());
			ps.setString(4, product.getBrand());
			ps.setDouble(5, product.getPrice());
			ps.setInt(6, product.getQuantity());
			ps.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
			ps.setString(8, product.getCreatedBy());
			ps.setDate(9, java.sql.Date.valueOf(LocalDate.now()));
			ps.setString(10, product.getModifiedBy());

			int rows = ps.executeUpdate();

			if (rows == 0) {
				throw new FailedToAddProductException("Failed to add product.");
			}

			return true;

		} catch (SQLException e) {
			throw new FailedToAddProductException("Failed to add product.", e);
		}
	}

	public void showAllProduct() throws SQLException {

		String sql = "SELECT * FROM product";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			System.out.println(
					"+-----+-------------------------+---------------+----------------------+-----------+-------+------------------------------------------------------------+");
			System.out.printf("| %-3s | %-23s | %-13s | %-20s | %-9s | %-5s | %-58s |%n", "ID", "Product Name",
					"Category", "Brand", "Price", "Stock", "Description");
			System.out.println(
					"+-----+-------------------------+---------------+----------------------+-----------+-------+------------------------------------------------------------+");

			while (rs.next()) {

				System.out.printf("| %-3d | %-23s | %-13s | %-20s | %9.2f | %5d | %-58s |%n", rs.getInt("product_id"),
						rs.getString("product_name"), rs.getString("category"), rs.getString("brand"),
						rs.getDouble("price"), rs.getInt("quantity"), rs.getString("product_description"));
			}

			System.out.println(
					"+-----+-------------------------+---------------+----------------------+-----------+-------+------------------------------------------------------------+");
		}
	}

	public Product getProductById(int productId) throws SQLException {

		String sql = "SELECT * FROM product WHERE product_id=?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, productId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				Product product = new Product();

				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductDescription(rs.getString("product_description"));
				product.setCategory(rs.getString("category"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getDouble("price"));
				product.setQuantity(rs.getInt("quantity"));
				product.setCreatedBy(rs.getString("created_by"));
				product.setCreatedDate(rs.getDate("created_date").toLocalDate());
				product.setModifiedBy(rs.getString("modified_by"));
				product.setModifiedDate(rs.getDate("modified_date").toLocalDate());
				return product;
			}
		}

		return null;
	}

	public boolean updateProduct(Product product, AppSession appSession) throws SQLException {

		String sql = "UPDATE product SET product_name=?,product_description=?,category=?,brand=?,price=?,quantity=?,modified_date=?,modified_by=? WHERE product_id=?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, product.getProductName());
			ps.setString(2, product.getProductDescription());
			ps.setString(3, product.getCategory());
			ps.setString(4, product.getBrand());
			ps.setDouble(5, product.getPrice());
			ps.setInt(6, product.getQuantity());
			ps.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
			ps.setString(8, appSession.getLoggedInUser().getCreatedBy());
			ps.setInt(9, product.getProductId());
			return ps.executeUpdate() > 0;
		}
	}
}
