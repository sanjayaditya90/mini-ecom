package com.ecom.cart;

import java.sql.SQLException;
import java.util.Scanner;

import com.ecom.app.AppSession;
import com.ecom.product.Product;
import com.ecom.product.ProductService;

public class CartService {

	private final Scanner sc = new Scanner(System.in);
	private final CartRepo cartRepo = new CartRepo();
	ProductService productService = new ProductService();
	AppSession appSession = new AppSession();

	public void addItemsToCart(Product product, int qty) throws SQLException {
		Cart cart = new Cart();
		cart.setCustomerId(appSession.getLoggedInUser().getUserId());
		cart.setProductId(product.getProductId());
		cart.setQuantity(qty);
		cart.setProductPrice(product.getPrice());
		cart.setProductName(product.getProductName());
		cart.setResellerId(product.getCreatedBy());
		cart.setResellerName(product.getCreatedBy());

		cartRepo.addItem(cart);
	}

	public void showCartItems() throws SQLException {
		cartRepo.getcartItems(appSession.getLoggedInUser().getUserId());
	}

	public void itemTobeAdded() throws SQLException {
		Product product = productService.getProduct();
		System.out.print("Enter Quantity : ");

		int qty = Integer.parseInt(sc.nextLine());
		addItemsToCart(product, qty);
	}

	public void itemToberemoved() throws SQLException {
		showCartItems();
		System.out.print("Enter your Product Id : ");

		int enteredId = Integer.parseInt(sc.nextLine());
		if (enteredId == 0) {
			System.out.println("Enter a valid Product Id !!");
		}
		cartRepo.removeItem(enteredId, appSession.getLoggedInUser().getUserId());

	}
}