package com.ecom.product;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.ecom.app.AppSession;
import com.ecom.user.FailedToCreateUserException;
import com.ecom.user.User;

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

	public Product getProducts() throws SQLException, Exception {
		Scanner sc = new Scanner(System.in);

		productRepo.showAllProduct();

		System.out.print("\nEnter Product ID to edit: ");
		int productId = Integer.parseInt(sc.nextLine());

		if (productId == 0) {
			throw new ProductNotFoundException("Product with id 0 is not available. Try Again!!");
		}

		return productRepo.getProductById(productId);
	}

	public Product showProduct() throws SQLException, Exception {
		Product product = getProducts();

		if (product == null) {
			System.out.println("Invalid Product Id");
			return null;
		}

		System.out.println("\nSelected Product");
		System.out.println("---------------------------");
		System.out.println("1. Name        : " + product.getProductName());
		System.out.println("2. Description : " + product.getProductDescription());
		System.out.println("3. Category    : " + product.getCategory());
		System.out.println("4. Brand       : " + product.getBrand());
		System.out.println("5. Price       : " + product.getPrice());
		System.out.println("6. Quantity    : " + product.getQuantity());

		UpdateProduct updateProduct = new UpdateProduct();
		product = updateProduct.showProductDetails(product);

		productRepo.updateProduct(product, appSession);
		return product;
	}

	public Product getProduct() throws SQLException {
		getAllProduct();
		System.out.print("Enter your Product Id : ");

		int enteredId = Integer.parseInt(sc.nextLine());
		if (enteredId == 0) {
			System.out.println("Enter a valid Product Id !!");
		}
		Product product = getProductById(enteredId);
		return product;
	}
	
	public void getAllProduct() throws SQLException {
		productRepo.showAllProduct();
	}

	public Product getProductById(int productId) throws SQLException {
		return productRepo.getProductById(productId);
	}
}
