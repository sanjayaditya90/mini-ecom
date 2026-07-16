package com.kode;

import com.kode.Porduct.Product;
import com.kode.cart.Cart;
import com.kode.cart.RemoveCart;
import com.kode.cart.ShowCart;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws SQLException, IOException, ClassNotFoundException {

        Scanner sc = new Scanner(System.in);
//        Product product = new Product();
//        product.addProduct();

        System.out.println("Enter 1 to add Product to cart Or Enter 2 to see Cart details 3 to remove product from Cart:");
        int n = sc.nextInt();

        switch (n) {
            case 1:
                System.out.println("Enter Product Id:");
                long id = sc.nextLong();
                sc.nextLine();

                System.out.println("Enter Product Name:");
                String name = sc.nextLine();

                System.out.println("Enter Quantity:");
                int quantity = sc.nextInt();
                sc.nextLine();
                Cart cart = new Cart(id, name, quantity);
                cart.addToCart(id, name, quantity);
            break;

            case 2:
                ShowCart cartItems = new ShowCart();
                cartItems.showCart();
            break;
            case 3:
                RemoveCart removeCart = new RemoveCart();
                System.out.println("Enter product id to remove");
                long productId = sc.nextLong();
                removeCart.removeProduct(productId);
            break;
            default:
                System.out.println("Wrong choice...");
        }

    }
}
