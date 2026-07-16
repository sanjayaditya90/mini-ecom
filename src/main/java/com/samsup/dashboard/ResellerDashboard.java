package com.samsup.dashboard;

import java.util.Scanner;

import com.samsup.Util.Utility;
import com.samsup.product.Product;
import com.samsup.product.ProductService;
import com.samsup.user.UserType;

public class ResellerDashboard implements ProductDashboard {

    private final Scanner sc = new Scanner(System.in);
    private final ProductService productService = new ProductService();
    private final Utility utility = new Utility();

    @Override
    public void listMenu() {

        while (true) {

            System.out.println("\n========== RESELLER DASHBOARD ==========");
            System.out.println("1. Upload Products using CSV");
            System.out.println("2. Add Product");
            System.out.println("3. Edit Product");
            System.out.println("4. Show Product");
            System.out.println("5. Exit");
            System.out.print("Enter your option : ");

            String userInput = sc.nextLine();

            try {

                switch (userInput) {

                case "1":
                    if (utility.ensureLoggedIn(UserType.RESELLER)) {
                        productService.uploadProducts();
                    }
                    break;

                case "2":
                    if (utility.ensureLoggedIn(UserType.RESELLER)) {
                        Product addProduct = productService.addProducts();
                        System.out.println("Product Added : "
                                + addProduct.getProductName()
                                + " By Reseller : "
                                + addProduct.getCreatedBy());
                    }
                    break;

                case "3":
                    if (utility.ensureLoggedIn(UserType.RESELLER)) {
                        Product updateProduct = productService.showProduct();
                        System.out.println("Product Updated : "
                                + updateProduct.getProductName()
                                + " By Reseller : "
                                + updateProduct.getModifiedBy());
                    }
                    break;

                case "4":
                    productService.getAllProduct();
                    break;

                case "5":
                    System.out.println("Returning to Main Dashboard...");
                    return;

                default:
                    System.out.println("Invalid option.");
                }

                System.out.println("\nPress Enter to continue...");
                sc.nextLine();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
