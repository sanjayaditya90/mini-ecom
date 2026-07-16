package com.ecom.dashboard;

import java.util.Scanner;

import com.ecom.app.AppSession;
import com.ecom.customer.CustomerLogin;
import com.ecom.reseller.ResellerLogin;
import com.ecom.user.User;
import com.ecom.user.UserLogin;
import com.ecom.user.UserNotFoundException;
import com.ecom.user.UserType;

public class LoginDashboard {

	public void loginPage() {

		Scanner sc = new Scanner(System.in);

		UserLogin customerLogin = new CustomerLogin();
		UserLogin resellerLogin = new ResellerLogin();
		AppSession appSession = new AppSession();
		MainDashboard mainDashboard = new MainDashboard();

		boolean exitMenu = false;

		while (!exitMenu) {

			System.out.println("\n========== LOGIN PAGE==========");
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
						User customer = customerLogin.login();
						appSession.setLoggedInUser(customer);

						System.out.println("Welcome " + customer.getFirstName());
						loadDashboard(customer);

					} catch (UserNotFoundException e) {

						System.out.println(e.getMessage());
						System.out.println("Redirecting to Login Page...");
						LoginDashboard loginDashboard = new LoginDashboard();
						loginDashboard.loginPage();
					}

					exitMenu = true;
					break;

				case "2":
					User newCustomer = customerLogin.registerUser(UserType.CUSTOMER);
					appSession.setLoggedInUser(newCustomer);

					System.out.println("Customer Registered Successfully.");
					System.out.println("Welcome " + newCustomer.getFirstName());
					loadDashboard(newCustomer);
					exitMenu = true;
					break;

				case "3":
					try {
						User resellerUser = resellerLogin.login(UserType.RESELLER);
						appSession.setLoggedInUser(resellerUser);
						System.out.println("Welcome " + resellerUser.getFirstName());
						loadDashboard(resellerUser);
					} catch (UserNotFoundException e) {
						System.out.println(e.getMessage());
						System.out.println("Redirecting to Login Page...");
						LoginDashboard loginDashboard = new LoginDashboard();
						loginDashboard.loginPage();
					}
					exitMenu = true;
					break;

				case "4":
					User newReseller = resellerLogin.registerUser(UserType.RESELLER);
					appSession.setLoggedInUser(newReseller);
					System.out.println("Reseller Registered Successfully.");
					System.out.println("Welcome " + newReseller.getFirstName());
					loadDashboard(newReseller);
					exitMenu = true;
					break;

				case "5":
					System.out.println("Returning to Main Dashboard...");
					return;

				default:
					System.out.println("Invalid option. Please try again.");
				}
				
				System.out.println("\nPress Enter to continue...");
				sc.nextLine();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void loadDashboard(User user) {

	    if (user.getUserType() == UserType.CUSTOMER) {
	        new CustomerDashboard().listMenu();
	    } else if (user.getUserType() == UserType.RESELLER) {
	        new ResellerDashboard().listMenu();
	    } else {
	        System.out.println("Invalid user type.");
	    }
	}
}
