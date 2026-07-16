package com.samsup.dashboard;

import java.util.Scanner;

import com.samsup.product.Product;
import com.samsup.product.ProductService;
import com.samsup.user.User;

public class ProductDashboard {
	public void listMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n========== RESELLER DASHBOARD ==========");
		System.out.println("1. Upload Products using CSV");
		System.out.println("2. Add Product");
		System.out.println("3. Edit Product");
		System.out.println("4. Show Product");
		System.out.println("5. Exit");
		System.out.print("Enter your option : ");

		String userInput = sc.nextLine();
		ProductService productService = new ProductService();
		try {
			switch (userInput) {

			case "1":
				productService.uploadProducts();
				break;

			case "2":
				Product addProduct = productService.addProducts();
				System.out.println("Product Add : " + addProduct.getProductName()
				+ " By Reseller : " + addProduct.getCreatedBy());
				break;

			case "3":
				Product updateProduct = productService.updateProducts();
				System.out.println("Product Updated :  " + updateProduct.getProductName()
				+ " By Reseller : " + updateProduct.getCreatedBy());
				break;

			case "4":

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
