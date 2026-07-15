package com.samsup.user;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.samsup.exception.FailedToCreateUserException;
import com.samsup.exception.FailedToGetUserException;
import com.samsup.exception.UserNotFoundException;

public class UserLogin {
	Scanner sc = new Scanner(System.in);

	public User registerUser(int userType) throws FailedToCreateUserException {
		// User Input
		UserRepo userRepo = new UserRepo();

		System.out.print("Enter your First name: ");
		String firstName = sc.nextLine();

		System.out.print("Enter your Last name: ");
		String lastName = sc.nextLine();

		System.out.print("Enter your Age: ");
		int age = Integer.parseInt(sc.nextLine());

		String userName;
		while (true) {

			System.out.print("Enter your User name: ");
			userName = sc.nextLine();

			if (userRepo.isUserNameExists(userName, userType)) {
				System.out.println("Username already exists. Please choose another username.");
			} else {
				break;
			}
		}

		System.out.print("Enter your Address: ");
		String address = sc.nextLine();

		String email;
		while (true) {

			System.out.print("Enter your Email: ");
			email = sc.nextLine();

			if (userRepo.isUserEmailExists(email, userType)) {
				System.out.println("Email already exists. Please choose another Email.");
			} else {
				break;
			}
		}

		System.out.print("Enter your Mobile No: ");
		String phoneNo = sc.nextLine();

		System.out.print("Enter your Password: ");
		String password = sc.nextLine();

		System.out.print("Verify Your Password: ");
		String verifyPassword = sc.nextLine();

		while (!password.equals(verifyPassword)) {
			System.out.println("Passwords do not match. Please try again.");

			System.out.print("Enter your Password: ");
			password = sc.nextLine();

			System.out.print("Verify Your Password: ");
			verifyPassword = sc.nextLine();
		}

		// Create User Object
		User user = new User();

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAge(age);
		user.setUserName(userName);
		user.setEmail(email);
		user.setPhoneNo(phoneNo);
		user.setPassword(password);
		user.setUserType(userType);

		user.setCreateDate(LocalDate.now());
		user.setCreatedBy(userName);

		user.setModifiedDate(LocalDate.now());
		user.setModifiedBy(userName);
		user.setAddress(address);

		// Save to database
		try {
			boolean isUserCreted = userRepo.saveUser(user);

			if (isUserCreted) {
				System.out.println("User Registered Successfully.");
				return user;
			}
		} catch (FailedToCreateUserException e) {
			throw new FailedToCreateUserException("User Login Failed..");
		}
		return new User();
	}

	public User login(int userType) throws SQLException, Exception {

		System.out.print("Enter your User name: ");
		String userName = sc.nextLine();

		System.out.print("Enter your Password: ");
		String password = sc.nextLine();

		UserRepo userRepo = new UserRepo();
		User user = userRepo.doValidateLogin(userName, password, userType);
		if (user == null) {
			throw new UserNotFoundException("User not found.");
		}
		return user;
	}
}
