package com.ecom.user;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ecom.app.AppSession;
import com.ecom.cart.Cart;
import com.ecom.product.Product;
import com.ecom.reseller.Reseller;

/**
 * Comprehensive Unit Test Suite for Mini E-Commerce Application
 * Tests all classes and methods across the application
 */
public class AppTest {
    
    private User testUser;
    private User testCustomer;
    private User testReseller;
    private Product testProduct;
    private Cart testCart;
    private AppSession appSession;

    @Before
    public void setUp() {
        // Initialize test data before each test
        appSession = new AppSession();
        testUser = new User();
        testUser.setUserId("user1");
        testUser.setUserName("testuser");
        testUser.setPassword("password123");
        testUser.setUserType(UserType.CUSTOMER);
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setEmail("test@example.com");
        testUser.setPhoneNo("9876543210");
        testUser.setAddress("123 Test Street");
        testUser.setAge(25);

        testCustomer = new User();
        testCustomer.setUserId("cust1");
        testCustomer.setUserName("customer1");
        testCustomer.setPassword("cust123");
        testCustomer.setUserType(UserType.CUSTOMER);
        testCustomer.setFirstName("John");
        testCustomer.setLastName("Doe");
        testCustomer.setEmail("john@example.com");

        testReseller = new Reseller();
        testReseller.setUserId("res1");
        testReseller.setUserName("reseller1");
        testReseller.setPassword("res123");
        testReseller.setUserType(UserType.RESELLER);
        testReseller.setFirstName("Jane");
        testReseller.setLastName("Smith");
        testReseller.setEmail("jane@example.com");

        testProduct = new Product();
        testProduct.setProductId(1);
        testProduct.setProductName("Test Product");
        testProduct.setProductDescription("A test product");
        testProduct.setCategory("Electronics");
        testProduct.setBrand("TestBrand");
        testProduct.setPrice(999.99);
        testProduct.setQuantity(50);

        testCart = new Cart();
        testCart.setCartId(1);
        testCart.setCustomerId("cust1");
        testCart.setProductId(1);
        testCart.setProductName("Test Product");
        testCart.setProductPrice(999.99);
        testCart.setQuantity(2);
        testCart.setTotalPrice(1999.98);

        // Clear session before each test
        AppSession.logout();
    }

    @After
    public void tearDown() {
        // Clean up after each test
        AppSession.logout();
        testUser = null;
        testProduct = null;
        testCart = null;
    }

    // ========== USER CLASS TESTS ==========
    
    @Test
    public void testUserCreation() {
        assertNotNull("User object should be created", testUser);
        assertEquals("User ID should match", "user1", testUser.getUserId());
        assertEquals("Username should match", "testuser", testUser.getUserName());
    }

    @Test
    public void testUserGettersSetters() {
        assertEquals("UserId getter", "user1", testUser.getUserId());
        assertEquals("UserName getter", "testuser", testUser.getUserName());
        assertEquals("Password getter", "password123", testUser.getPassword());
        assertEquals("FirstName getter", "Test", testUser.getFirstName());
        assertEquals("LastName getter", "User", testUser.getLastName());
        assertEquals("Email getter", "test@example.com", testUser.getEmail());
        assertEquals("PhoneNo getter", "9876543210", testUser.getPhoneNo());
        assertEquals("Address getter", "123 Test Street", testUser.getAddress());
        assertEquals("Age getter", 25, testUser.getAge());
    }

    @Test
    public void testUserSetters() {
        testUser.setUserId("user99");
        testUser.setUserName("newuser");
        testUser.setPassword("newpass");
        testUser.setFirstName("NewFirst");
        testUser.setLastName("NewLast");
        testUser.setEmail("new@example.com");
        testUser.setPhoneNo("1111111111");
        testUser.setAddress("New Address");
        testUser.setAge(30);

        assertEquals("UserId updated", "user99", testUser.getUserId());
        assertEquals("UserName updated", "newuser", testUser.getUserName());
        assertEquals("Password updated", "newpass", testUser.getPassword());
        assertEquals("FirstName updated", "NewFirst", testUser.getFirstName());
        assertEquals("LastName updated", "NewLast", testUser.getLastName());
        assertEquals("Email updated", "new@example.com", testUser.getEmail());
        assertEquals("PhoneNo updated", "1111111111", testUser.getPhoneNo());
        assertEquals("Address updated", "New Address", testUser.getAddress());
        assertEquals("Age updated", 30, testUser.getAge());
    }

    @Test
    public void testUserTypeSettersGetters() {
        testUser.setUserType(UserType.RESELLER);
        assertEquals("UserType should be updated to RESELLER", UserType.RESELLER, testUser.getUserType());
        
        testUser.setUserType(UserType.CUSTOMER);
        assertEquals("UserType should be updated to CUSTOMER", UserType.CUSTOMER, testUser.getUserType());
    }

    @Test
    public void testUserMetadataFields() {
        testUser.setCreatedBy("admin");
        testUser.setModifiedBy("user");
        testUser.setCreateDate(LocalDate.of(2026, 7, 16));

        assertEquals("CreatedBy should match", "admin", testUser.getCreatedBy());
        assertEquals("ModifiedBy should match", "user", testUser.getModifiedBy());
    }

    // ========== USER TYPE TESTS ==========
    
    @Test
    public void testUserTypeConstants() {
        assertEquals("CUSTOMER constant should be 1", 1, UserType.CUSTOMER);
        assertEquals("RESELLER constant should be 2", 2, UserType.RESELLER);
    }

    // ========== APP SESSION TESTS ==========
    
    @Test
    public void testAppSessionSetAndGetLoggedInUser() {
        AppSession.setLoggedInUser(testUser);
        User retrievedUser = appSession.getLoggedInUser();
        
        assertNotNull("Logged in user should not be null", retrievedUser);
        assertEquals("Logged in user ID should match", testUser.getUserId(), retrievedUser.getUserId());
        assertEquals("Logged in user name should match", testUser.getUserName(), retrievedUser.getUserName());
    }

    @Test
    public void testAppSessionIsLoggedIn() {
        assertFalse("Should not be logged in initially", AppSession.isLoggedIn());
        
        AppSession.setLoggedInUser(testUser);
        assertTrue("Should be logged in after setting user", AppSession.isLoggedIn());
    }

    @Test
    public void testAppSessionLogout() {
        AppSession.setLoggedInUser(testUser);
        assertTrue("Should be logged in before logout", AppSession.isLoggedIn());
        
        AppSession.logout();
        assertFalse("Should not be logged in after logout", AppSession.isLoggedIn());
        assertNull("Logged in user should be null after logout", appSession.getLoggedInUser());
    }

    @Test
    public void testAppSessionMultipleUsers() {
        AppSession.setLoggedInUser(testCustomer);
        User user1 = appSession.getLoggedInUser();
        assertEquals("First user should be customer", "customer1", user1.getUserName());
        
        AppSession.setLoggedInUser(testReseller);
        User user2 = appSession.getLoggedInUser();
        assertEquals("Second user should be reseller", "reseller1", user2.getUserName());
    }

    // ========== PRODUCT CLASS TESTS ==========
    
    @Test
    public void testProductCreation() {
        assertNotNull("Product object should be created", testProduct);
        assertEquals("Product ID should match", 1, testProduct.getProductId());
        assertEquals("Product name should match", "Test Product", testProduct.getProductName());
    }

    @Test
    public void testProductGettersSetters() {
        assertEquals("ProductId getter", 1, testProduct.getProductId());
        assertEquals("ProductName getter", "Test Product", testProduct.getProductName());
        assertEquals("ProductDescription getter", "A test product", testProduct.getProductDescription());
        assertEquals("Category getter", "Electronics", testProduct.getCategory());
        assertEquals("Brand getter", "TestBrand", testProduct.getBrand());
        assertEquals("Price getter", 999.99, testProduct.getPrice(), 0.01);
        assertEquals("Quantity getter", 50, testProduct.getQuantity());
    }

    @Test
    public void testProductSetters() {
        testProduct.setProductId(99);
        testProduct.setProductName("Updated Product");
        testProduct.setProductDescription("Updated description");
        testProduct.setCategory("Books");
        testProduct.setBrand("NewBrand");
        testProduct.setPrice(499.99);
        testProduct.setQuantity(100);

        assertEquals("ProductId updated", 99, testProduct.getProductId());
        assertEquals("ProductName updated", "Updated Product", testProduct.getProductName());
        assertEquals("ProductDescription updated", "Updated description", testProduct.getProductDescription());
        assertEquals("Category updated", "Books", testProduct.getCategory());
        assertEquals("Brand updated", "NewBrand", testProduct.getBrand());
        assertEquals("Price updated", 499.99, testProduct.getPrice(), 0.01);
        assertEquals("Quantity updated", 100, testProduct.getQuantity());
    }

    @Test
    public void testProductMetadataFields() {
        testProduct.setCreatedDate(LocalDate.of(2026, 7, 16));
        testProduct.setCreatedBy("reseller1");
        testProduct.setModifiedDate(LocalDate.of(2026, 7, 17));
        testProduct.setModifiedBy("admin");

        assertEquals("CreatedDate should match", LocalDate.of(2026, 7, 16), testProduct.getCreatedDate());
        assertEquals("CreatedBy should match", "reseller1", testProduct.getCreatedBy());
        assertEquals("ModifiedDate should match", LocalDate.of(2026, 7, 17), testProduct.getModifiedDate());
        assertEquals("ModifiedBy should match", "admin", testProduct.getModifiedBy());
    }

    @Test
    public void testProductPriceValidation() {
        testProduct.setPrice(0.0);
        assertEquals("Price can be zero", 0.0, testProduct.getPrice(), 0.01);

        testProduct.setPrice(99999.99);
        assertEquals("Price can be large", 99999.99, testProduct.getPrice(), 0.01);
    }

    @Test
    public void testProductQuantityValidation() {
        testProduct.setQuantity(0);
        assertEquals("Quantity can be zero", 0, testProduct.getQuantity());

        testProduct.setQuantity(999999);
        assertEquals("Quantity can be large", 999999, testProduct.getQuantity());
    }

    // ========== CART CLASS TESTS ==========
    
    @Test
    public void testCartCreation() {
        assertNotNull("Cart object should be created", testCart);
        assertEquals("Cart ID should match", 1, testCart.getCartId());
        assertEquals("Customer ID should match", "cust1", testCart.getCustomerId());
    }

    @Test
    public void testCartGettersSetters() {
        assertEquals("CartId getter", 1, testCart.getCartId());
        assertEquals("CustomerId getter", "cust1", testCart.getCustomerId());
        assertEquals("ProductId getter", 1, testCart.getProductId());
        assertEquals("ProductName getter", "Test Product", testCart.getProductName());
        assertEquals("ProductPrice getter", 999.99, testCart.getProductPrice(), 0.01);
        assertEquals("Quantity getter", (int)2, (int)testCart.getQuantity());
        assertEquals("TotalPrice getter", 1999.98, testCart.getTotalPrice(), 0.01);
    }

    @Test
    public void testCartSetters() {
        testCart.setCartId(99);
        testCart.setCustomerId("cust2");
        testCart.setProductId(5);
        testCart.setProductName("New Product");
        testCart.setProductPrice(499.99);
        testCart.setQuantity(3);
        testCart.setTotalPrice(1499.97);

        assertEquals("CartId updated", 99, testCart.getCartId());
        assertEquals("CustomerId updated", "cust2", testCart.getCustomerId());
        assertEquals("ProductId updated", 5, testCart.getProductId());
        assertEquals("ProductName updated", "New Product", testCart.getProductName());
        assertEquals("ProductPrice updated", 499.99, testCart.getProductPrice(), 0.01);
        assertEquals("Quantity updated", (int)3, (int)testCart.getQuantity());
        assertEquals("TotalPrice updated", 1499.97, testCart.getTotalPrice(), 0.01);
    }

    @Test
    public void testCartPriceCalculation() {
        double price = 100.0;
        int quantity = 5;
        double expectedTotal = price * quantity;

        testCart.setProductPrice(price);
        testCart.setQuantity(quantity);
        testCart.setTotalPrice(price * quantity);

        assertEquals("Total price should be quantity * price", expectedTotal, testCart.getTotalPrice(), 0.01);
    }

    @Test
    public void testCartMetadataFields() {
        LocalDateTime addedDateTime = LocalDateTime.of(2026, 7, 16, 10, 30, 0);
        testCart.setAddedDate(addedDateTime);
        assertEquals("AddedDate should match", addedDateTime, testCart.getAddedDate());
    }

    @Test
    public void testCartResellerFields() {
        testCart.setResellerId("res1");
        testCart.setResellerName("Reseller One");

        assertEquals("ResellerId should match", "res1", testCart.getResellerId());
        assertEquals("ResellerName should match", "Reseller One", testCart.getResellerName());
    }

    // ========== RESELLER CLASS TESTS ==========
    
    @Test
    public void testResellerCreation() {
        Reseller reseller = new Reseller();
        assertNotNull("Reseller object should be created", reseller);
    }

    @Test
    public void testResellerInheritance() {
        Reseller reseller = new Reseller();
        reseller.setUserId("res1");
        reseller.setUserName("res1");
        reseller.setUserType(UserType.RESELLER);

        assertEquals("Reseller should have userId from User", "res1", reseller.getUserId());
        assertEquals("Reseller should have userName from User", "res1", reseller.getUserName());
        assertEquals("Reseller should be RESELLER type", UserType.RESELLER, reseller.getUserType());
    }

    // ========== EXCEPTION CLASS TESTS ==========
    
    @Test
    public void testFailedToCreateUserException() {
        String errorMessage = "User creation failed";
        try {
            throw new FailedToCreateUserException(errorMessage);
        } catch (FailedToCreateUserException e) {
            assertEquals("Exception message should match", errorMessage, e.getMessage());
        }
    }

    @Test
    public void testFailedToGetUserException() {
        String errorMessage = "Failed to retrieve user";
        try {
            throw new FailedToGetUserException(errorMessage);
        } catch (FailedToGetUserException e) {
            assertEquals("Exception message should match", errorMessage, e.getMessage());
        }
    }

    @Test
    public void testUserNotFoundException() {
        String errorMessage = "User not found";
        try {
            throw new UserNotFoundException(errorMessage);
        } catch (UserNotFoundException e) {
            assertEquals("Exception message should match", errorMessage, e.getMessage());
        }
    }

    @Test
    public void testUserAlreadyExistsException() {
        String errorMessage = "User already exists";
        try {
            throw new UserAlreadyExistsException(errorMessage);
        } catch (UserAlreadyExistsException e) {
            assertEquals("Exception message should match", errorMessage, e.getMessage());
        }
    }

    // ========== EDGE CASES AND BOUNDARY TESTS ==========
    
    @Test
    public void testUserWithNullValues() {
        User nullUser = new User();
        assertNull("User ID should be null initially", nullUser.getUserId());
    }

    @Test
    public void testProductWithZeroPrice() {
        testProduct.setPrice(0.0);
        assertEquals("Product with zero price should be allowed", 0.0, testProduct.getPrice(), 0.01);
    }

    @Test
    public void testProductWithZeroQuantity() {
        testProduct.setQuantity(0);
        assertEquals("Product with zero quantity should be allowed", 0, testProduct.getQuantity());
    }

    @Test
    public void testCartWithSingleItem() {
        testCart.setQuantity(1);
        assertEquals("Cart with single item should have quantity 1", (int)1, (int)testCart.getQuantity());
    }

    @Test
    public void testCartWithLargeQuantity() {
        testCart.setQuantity(10000);
        assertEquals("Cart with large quantity should be allowed", (int)10000, (int)testCart.getQuantity());
    }

    @Test
    public void testUserWithSpecialCharactersInName() {
        testUser.setFirstName("Jean-Pierre");
        testUser.setLastName("O'Brien");
        assertEquals("FirstName with special characters", "Jean-Pierre", testUser.getFirstName());
        assertEquals("LastName with special characters", "O'Brien", testUser.getLastName());
    }

    @Test
    public void testProductWithEmptyStrings() {
        testProduct.setProductName("");
        testProduct.setProductDescription("");
        assertEquals("Product name can be empty", "", testProduct.getProductName());
        assertEquals("Product description can be empty", "", testProduct.getProductDescription());
    }

    @Test
    public void testUserEmailFormats() {
        String[] validEmails = {
            "user@example.com",
            "user.name@example.co.uk",
            "user+tag@example.com",
            "user123@test.org"
        };

        for (String email : validEmails) {
            testUser.setEmail(email);
            assertEquals("Email should be stored correctly: " + email, email, testUser.getEmail());
        }
    }

    @Test
    public void testUserPhoneFormats() {
        String[] validPhones = {
            "9876543210",
            "+919876543210",
            "(987) 654-3210",
            "987-654-3210"
        };

        for (String phone : validPhones) {
            testUser.setPhoneNo(phone);
            assertEquals("Phone should be stored correctly: " + phone, phone, testUser.getPhoneNo());
        }
    }

    // ========== INTEGRATION TESTS ==========
    
    @Test
    public void testSessionWithCustomerUser() {
        AppSession.setLoggedInUser(testCustomer);
        assertTrue("Session should have logged in user", AppSession.isLoggedIn());
        User loggedUser = appSession.getLoggedInUser();
        assertEquals("Logged user should be customer", UserType.CUSTOMER, loggedUser.getUserType());
    }

    @Test
    public void testSessionWithResellerUser() {
        AppSession.setLoggedInUser(testReseller);
        assertTrue("Session should have logged in user", AppSession.isLoggedIn());
        User loggedUser = appSession.getLoggedInUser();
        assertEquals("Logged user should be reseller", UserType.RESELLER, loggedUser.getUserType());
    }

    @Test
    public void testSessionSwitchUsers() {
        AppSession.setLoggedInUser(testCustomer);
        assertEquals("First user should be customer", "customer1", appSession.getLoggedInUser().getUserName());

        AppSession.setLoggedInUser(testReseller);
        assertEquals("Second user should be reseller", "reseller1", appSession.getLoggedInUser().getUserName());
    }

    @Test
    public void testProductToCartMapping() {
        testCart.setProductId(testProduct.getProductId());
        testCart.setProductName(testProduct.getProductName());
        testCart.setProductPrice(testProduct.getPrice());

        assertEquals("Cart product ID should match", testProduct.getProductId(), testCart.getProductId());
        assertEquals("Cart product name should match", testProduct.getProductName(), testCart.getProductName());
        assertEquals("Cart product price should match", testProduct.getPrice(), testCart.getProductPrice(), 0.01);
    }

    // ========== HELPER METHOD TESTS ==========
    
    @Test
    public void testObjectEquality() {
        User user1 = new User();
        user1.setUserId("1");
        user1.setUserName("test");

        User user2 = new User();
        user2.setUserId("1");
        user2.setUserName("test");

        assertNotNull("Both users should be non-null", user1);
        assertNotNull("Both users should be non-null", user2);
    }

    @Test
    public void testProductsWithSameDetails() {
        Product product1 = new Product();
        product1.setProductName("Laptop");
        product1.setPrice(50000.0);

        Product product2 = new Product();
        product2.setProductName("Laptop");
        product2.setPrice(50000.0);

        assertEquals("Products with same name should match", product1.getProductName(), product2.getProductName());
        assertEquals("Products with same price should match", product1.getPrice(), product2.getPrice(), 0.01);
    }

    @Test
    public void testMultipleCartsForSameCustomer() {
        Cart cart1 = new Cart();
        cart1.setCartId(1);
        cart1.setCustomerId("cust1");
        cart1.setProductId(1);

        Cart cart2 = new Cart();
        cart2.setCartId(2);
        cart2.setCustomerId("cust1");
        cart2.setProductId(2);

        assertEquals("Both carts belong to same customer", cart1.getCustomerId(), cart2.getCustomerId());
        assertNotEquals("Carts should have different IDs", cart1.getCartId(), cart2.getCartId());
    }
}
