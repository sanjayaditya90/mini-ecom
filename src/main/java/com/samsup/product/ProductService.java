package com.samsup.product;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.samsup.dashboard.AppSession;
import com.samsup.user.FailedToCreateUserException;
import com.samsup.user.User;

public class ProductService {

	Scanner sc = new Scanner(System.in);
	AppSession appSession = new AppSession();
	ProductRepo productRepo = new ProductRepo();

	public void uploadProducts() throws Exception {

		System.out.println("Enter the full path of the CSV file:");
		String filePath = sc.nextLine();

		File file = new File(filePath);

		if (!file.exists()) {
			System.out.println("File does not exist.");
			return;
		}

		if (!file.getName().toLowerCase().endsWith(".csv")) {
			System.out.println("Please select a CSV file.");
			return;
		}

		productRepo.saveProduct(appSession, filePath);
	}

	public Product addProducts() throws FailedToAddProductException {

		System.out.println("Enter the Product Name:");
		String productName = sc.nextLine();

		System.out.println("Enter the Product Description:");
		String productDescription = sc.nextLine();

		System.out.println("Enter the Category:");
		String category = sc.nextLine();

		System.out.println("Enter the Brand:");
		String brand = sc.nextLine();

		System.out.println("Enter the Price:");
		double price = Double.parseDouble(sc.nextLine());

		System.out.println("Enter the Quantity:");
		int quantity = Integer.parseInt(sc.nextLine());

		Product product = new Product();

		product.setProductName(productName);
		product.setProductDescription(productDescription);
		product.setCategory(category);
		product.setBrand(brand);
		product.setPrice(price);
		product.setQuantity(quantity);
		product.setCreatedBy(appSession.getLoggedInUser().getUserName());
		product.setCreatedDate(LocalDate.now());
		product.setModifiedBy(appSession.getLoggedInUser().getUserName());
		product.setModifiedDate(LocalDate.now());

		// Save to database
		try {
			boolean isProductCreted = productRepo.saveProduct(product);

			if (isProductCreted) {
				System.out.println("Product Add Successfully.");
				return product;
			}
		} catch (FailedToAddProductException e) {
			throw new FailedToAddProductException("Product Addition Failed..");
		}
		return new Product();

	}

	public Product updateProducts() throws SQLException {
		productRepo.showAllProduct();
		return null;
	}
}
