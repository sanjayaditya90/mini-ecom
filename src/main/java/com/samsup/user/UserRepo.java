package com.samsup.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.samsup.exception.FailedToCreateUserException;
import com.samsup.exception.FailedToGetUserException;

public class UserRepo {
	public PreparedStatement ps;
	public Connection connection;

	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/9th_march_2026", "root",
					"strong@15104961");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean saveUser(User user) throws FailedToCreateUserException {

		String saveUserSql = "INSERT INTO customer "
				+ "(user_name, password, first_name, last_name, email, phone_no, age, create_date, created_by, modified_date, modified_by) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = connection.prepareStatement(saveUserSql)) {

			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getPhoneNo());
			ps.setInt(7, user.getAge());

			ps.setDate(8, java.sql.Date.valueOf(user.getCreateDate()));
			ps.setString(9, user.getCreatedBy());

			ps.setDate(10, java.sql.Date.valueOf(user.getModifiedDate()));
			ps.setString(11, user.getModifiedBy());

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("User Saved Successfully.");
			}

			if (rows == 0) {
				throw new FailedToCreateUserException("Failed to create user.");
			}

			return true;

		} catch (SQLException e) {
			throw new FailedToCreateUserException("Failed to create user.", e);
		}
	}

	public User doValidateLogin(String userName, String password) throws SQLException, FailedToGetUserException {

		String loginQuery = "Select * from customer where user_id = ? and password = ?";

		try (PreparedStatement ps = connection.prepareStatement(loginQuery)) {
			ps.setString(1, userName);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			User user = new User();
			while (rs != null && rs.next()) {
				user.setUserId("user_name");
				user.setPassword("password");
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUserName(rs.getString("user_id"));
				user.setPhoneNo(rs.getString("phone_no"));
				user.setAddress(rs.getString("address"));
				user.setEmail(rs.getString("email"));
				user.setAge(rs.getInt("age"));
				user.setCreatedBy(rs.getString("created_by"));
				user.setCreateDate(rs.getDate("create_date").toLocalDate());
				user.setModifiedBy("modified_by");
				user.setModifiedDate(rs.getDate("modified_date").toLocalDate());
			}
			return user;

		} catch (SQLException e) {
			throw new FailedToGetUserException("Failed to retrieve user.", e);
		}
	}

	public boolean isUserNameExists(String userName) {
		String sql = "SELECT COUNT(*) FROM customer WHERE user_name = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, userName);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			
		}

		return false;
	}
}
