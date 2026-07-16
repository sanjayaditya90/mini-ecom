package com.samsup.dashboard;

import java.util.Scanner;

import com.samsup.app.AppSession;
import com.samsup.user.User;
import com.samsup.user.UserType;

public class MainDashboard {

	public void dashboardPage() {

		AppSession appSession = new AppSession();
		Scanner sc = new Scanner(System.in);
		LoginDashboard appLogin = new LoginDashboard();
		ResellerDashboard resellerDashboard = new ResellerDashboard();

		while (true) {
			User loginUser = appSession.getLoggedInUser();
			System.out.println("\n========== MAIN DASHBOARD ==========");
			if (loginUser == null) {
			    System.out.println("Welcome Guest User");
			    System.out.println("1. Login");
			} else {
			    System.out.println("Welcome " + loginUser.getFirstName());
			}
			System.out.println("2. Product");
			if (loginUser != null) {
			    System.out.println("3. Logout");
			}
			System.out.print("Enter your option : ");

			String userInput = sc.nextLine();

			try {
				switch (userInput) {

				case "1":
					if (loginUser == null) {
						appLogin.loginPage();
					} else {
						System.out.println("You are already logged in.");
					}
					break;

				case "2":

					int userType;
				    if (loginUser == null) {
				        userType = UserType.CUSTOMER;
				    }else {
				    	userType = loginUser.getUserType();
				    }

				    ProductDashboard dashboard = new CustomerDashboard();

				    switch (userType) {

				    case UserType.CUSTOMER:
				        dashboard = new CustomerDashboard();
				        break;

				    case UserType.RESELLER:
				        dashboard = new ResellerDashboard();
				        break;

				    default:
				        System.out.println("Invalid User Type.");
				        break;
				    }

				    if (dashboard != null) {
				        dashboard.listMenu();
				    }

				    break;

				case "3":
					if (loginUser != null) {
						appSession.logout();
						System.out.println("Logged out successfully.");
					} else {
						System.out.println("No user is currently logged in.");
					}
					break;
				default:
					System.out.println("Invalid option. Please try again.");
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
