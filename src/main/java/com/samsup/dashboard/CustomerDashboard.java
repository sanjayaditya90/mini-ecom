package com.samsup.dashboard;

import java.util.Scanner;

import com.samsup.Util.Utility;
import com.samsup.product.ProductService;
import com.samsup.user.UserType;

public class CustomerDashboard implements ProductDashboard {

    private final Scanner sc = new Scanner(System.in);
    private final ProductService productService = new ProductService();
//    private final OrderService orderService = new OrderService(); // optional
    private final Utility utility = new Utility();

    @Override
    public void listMenu() {

        while (true) {

            System.out.println("\n========== CUSTOMER DASHBOARD ==========");
            System.out.println("1. View Products");
            System.out.println("2. Add To Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. My Orders");
            System.out.println("6. Exit");
            System.out.print("Enter your option : ");

            String userInput = sc.nextLine();

            try {

                switch (userInput) {

                case "1":
                    productService.getAllProduct();
                    break;

                case "2":
                    if (utility.ensureLoggedIn(UserType.CUSTOMER)) {
                        System.out.println("Add To Cart Logic");
                    }
                    break;

                case "3":
                    if (utility.ensureLoggedIn(UserType.CUSTOMER)) {
                        System.out.println("View Cart Logic");
                    }
                    break;

                case "4":
                    if (utility.ensureLoggedIn(UserType.CUSTOMER)) {
                        System.out.println("Place Order Logic");
                    }
                    break;

                case "5":
                    if (utility.ensureLoggedIn(UserType.CUSTOMER)) {
                        System.out.println("My Orders Logic");
                    }
                    break;

                case "6":
                    System.out.println("Returning to Main Dashboard...");
                    return;

                default:
                    System.out.println("Invalid Option");
                }

                System.out.println("\nPress Enter to continue...");
                sc.nextLine();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
