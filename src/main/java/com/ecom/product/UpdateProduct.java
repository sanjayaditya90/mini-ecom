package com.ecom.product;

import java.util.Scanner;

public class UpdateProduct {
	
	Scanner sc = new Scanner(System.in);

	public Product showProductDetails(Product product) {
		System.out.println("\nWhich field do you want to edit?");
		System.out.println("1. Product Name");
		System.out.println("2. Description");
		System.out.println("3. Category");
		System.out.println("4. Brand");
		System.out.println("5. Price");
		System.out.println("6. Quantity");

		int choice = Integer.parseInt(sc.nextLine());
		
		switch(choice) {

		case 1:
		    System.out.print("Enter New Product Name : ");
		    product.setProductName(sc.nextLine());
		    break;

		case 2:
		    System.out.print("Enter New Description : ");
		    product.setProductDescription(sc.nextLine());
		    break;

		case 3:
		    System.out.print("Enter New Category : ");
		    product.setCategory(sc.nextLine());
		    break;

		case 4:
		    System.out.print("Enter New Brand : ");
		    product.setBrand(sc.nextLine());
		    break;

		case 5:
		    System.out.print("Enter New Price : ");
		    product.setPrice(Double.parseDouble(sc.nextLine()));
		    break;

		case 6:
		    System.out.print("Enter New Quantity : ");
		    product.setQuantity(Integer.parseInt(sc.nextLine()));
		    break;

		default:
		    System.out.println("Invalid Choice");
		    return null;
		}
		return product;
	}
}
