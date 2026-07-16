package com.samsup.dashboard;

import java.util.Scanner;

import com.samsup.customer.CustomerLogin;
import com.samsup.reseller.ResellerLogin;
import com.samsup.user.User;
import com.samsup.user.UserLogin;
import com.samsup.user.UserNotFoundException;
import com.samsup.user.UserType;

public class LoginDashboard {
  public void loginPage() {
	  Scanner sc = new Scanner(System.in);

		UserLogin userLogin = new UserLogin();
		UserLogin customerLogin = new CustomerLogin();
		UserLogin resellerLogin = new ResellerLogin();
		AppSession appSession = new AppSession();

		boolean exitMenu = false;

		while (!exitMenu) {

			System.out.println("\n========== LOGIN ==========");
			System.out.println("1. Customer Login");
			System.out.println("2. Register Customer");
			System.out.println("3. Reseller Login");
			System.out.println("4. Register Reseller");
			System.out.println("5. Exit");
			System.out.print("Enter your option: ");

			String userInput = sc.nextLine();

			try {

				switch (userInput) {

				case "1":
					try {
						User customer = customerLogin.login(UserType.CUSTOMER);

						appSession.setLoggedInUser(customer);
						System.out.println("Welcome " + customer.getUserName());

					} catch (UserNotFoundException e) {

						System.out.println(e.getMessage());
						System.out.println("Redirecting to registration...");

						User customer = customerLogin.registerUser();
						appSession.setLoggedInUser(customer);

						System.out.println("Welcome " + customer.getUserName());
					}
					exitMenu = true;
					break;

				case "2":
					User newCustomer = customerLogin.registerUser(UserType.CUSTOMER);
					appSession.setLoggedInUser(newCustomer);
					System.out.println("Customer Registered Successfully.");
					System.out.println("Welcome " + newCustomer.getUserName());
					exitMenu = true;
					break;

				case "3":
					try {
						User resellerUser = resellerLogin.login(UserType.RESELLER);
						appSession.setLoggedInUser(resellerUser);
						System.out.println("Welcome " + resellerUser.getUserName());
					} catch (UserNotFoundException e) {
						System.out.println(e.getMessage());
						System.out.println("Redirecting to registration...");

						User resellerUser = resellerLogin.registerUser(UserType.RESELLER);
						appSession.setLoggedInUser(resellerUser);

						System.out.println("Welcome " + resellerUser.getUserName());
					}
					exitMenu = true;
					break;

				case "4":
					User newReseller = resellerLogin.registerUser(UserType.RESELLER);
					appSession.setLoggedInUser(newReseller);
					System.out.println("Reseller Registered Successfully.");
					System.out.println("Welcome " + newReseller.getUserName());
					exitMenu = true;
					break;

				case "5":
					System.out.println("Thank you for using the application.");
					return; 
					
				default:
					System.out.println("Invalid option. Please try again.");
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
  }
}
