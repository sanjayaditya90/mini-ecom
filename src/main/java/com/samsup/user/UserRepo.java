package com.samsup.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {
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

	public boolean saveUser(User user) throws FailedToCreateUserException {

		String saveUserSql = "INSERT INTO customer "
				+ "(user_name, password, first_name, last_name, email, phone_no, age, created_date, created_by, modified_date, modified_by, user_type, address) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
			ps.setInt(12, user.getUserType());
			ps.setString(13, user.getAddress());

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("User Saved Successfully.");
			}

			if (rows == 0) {
				throw new FailedToCreateUserException("Failed to create user.");
			}

			return true;

		} catch (SQLException e) {
			throw new FailedToCreateUserException("Failed to create user." + e);
		}
	}

	public User doValidateLogin(String userName, String password, int userType)
			throws SQLException, FailedToGetUserException {

		String loginQuery = "Select * from customer where user_name = ? and password = ? and user_type = ?";

		try (PreparedStatement ps = connection.prepareStatement(loginQuery)) {
			ps.setString(1, userName);
			ps.setString(2, password);
			ps.setInt(3, userType);

			ResultSet rs = ps.executeQuery();
			User user = null;
			if (rs.next()) {

				user = new User();
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setUserType(rs.getInt("user_type"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUserId(rs.getString("user_id"));
				user.setPhoneNo(rs.getString("phone_no"));
				user.setAddress(rs.getString("address"));
				user.setEmail(rs.getString("email"));
				user.setAge(rs.getInt("age"));
				user.setCreatedBy(rs.getString("created_by"));
				user.setCreateDate(rs.getDate("created_date").toLocalDate());
				user.setModifiedBy(rs.getString("modified_by"));
				user.setModifiedDate(rs.getDate("modified_date").toLocalDate());
			}
			return user;

		} catch (SQLException e) {
			throw new FailedToGetUserException("Failed to retrieve user." + e);
		}
	}

	public boolean isUserNameExists(String userName, int userType) {
		String sql = "SELECT COUNT(*) FROM customer WHERE user_name = ? and user_type = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, userName);
			ps.setInt(2, userType);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {

		}

		return false;
	}

	public boolean isUserEmailExists(String email, int userType) {
		String sql = "SELECT COUNT(*) FROM customer WHERE email = ? and user_type = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, email);
			ps.setInt(2, userType);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {

		}

		return false;
	}
}
