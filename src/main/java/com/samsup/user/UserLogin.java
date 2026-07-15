package com.samsup.user;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.samsup.exception.FailedToCreateUserException;
import com.samsup.exception.FailedToGetUserException;

public class UserLogin {

	public void registerUser() throws FailedToCreateUserException {
		//User Input
		Scanner sc = new Scanner(System.in);
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

		    if (userRepo.isUserNameExists(userName)) {
		        System.out.println("Username already exists. Please choose another username.");
		    } else {
		        break;
		    }
		}

		System.out.print("Enter your Email: ");
		String email = sc.nextLine();

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

		user.setCreateDate(LocalDate.now());
		user.setCreatedBy(userName);

		user.setModifiedDate(LocalDate.now());
		user.setModifiedBy(userName);

		// Save to database
		try {
			boolean isUserCreted = userRepo.saveUser(user);
			
			if (isUserCreted) {
				System.out.println("User Registered Successfully.");
			}
		} catch (FailedToCreateUserException e) {
			throw new FailedToCreateUserException("User Login Failed..");
		}
	}

	public void userLogin(String userId, String password) throws SQLException, FailedToGetUserException {
		UserRepo userRepo = new UserRepo();
		User user = userRepo.doValidateLogin(userId, password);
	}
}
