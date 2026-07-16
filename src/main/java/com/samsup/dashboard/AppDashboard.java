package com.samsup.dashboard;

import java.util.Scanner;

import com.samsup.product.Product;
import com.samsup.user.User;
import com.samsup.user.UserType;

public class AppDashboard {

	public void dashboardPage() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n========== DASHBOARD ==========");
		System.out.println("1. Login");
		System.out.println("2. Product");
		System.out.println("3. Orders");
		System.out.println("4. Cart ");
		System.out.println("5. Logout");
		System.out.print("Enter your option : ");

		String userInput = sc.nextLine();

		try {
			switch (userInput) {

			case "1":
				LoginDashboard appLogin = new LoginDashboard();
				appLogin.loginPage();
				break;

			case "2":
				ProductDashboard appProduct = new ProductDashboard();
				appProduct.listMenu();
				break;

			case "3":
				// add order logic
				break;

			case "4":
				// add cart logic
				break;

			case "5":
				// Add logout logic
				AppSession appSession = new AppSession();
				appSession.logout();
				AppDashboard appDashboard = new AppDashboard();
				appDashboard.dashboardPage();
				return;

			default:
				System.out.println("Invalid option. Please try again.");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
