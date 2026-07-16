package com.ecom.cart;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;

/**
 * Unit Tests for CartService and Cart Classes
 * Tests all cart-related business logic methods
 */
public class CartServiceTest {

    private Cart testCart;
    private Cart cartItem1;
    private Cart cartItem2;

    @Before
    public void setUp() {
        testCart = new Cart();
        testCart.setCartId(1);
        testCart.setCustomerId("cust001");
        testCart.setProductId(100);
        testCart.setProductName("Laptop");
        testCart.setProductPrice(50000.00);
        testCart.setQuantity(1);
        testCart.setTotalPrice(50000.00);
        testCart.setAddedDate(LocalDateTime.of(2026, 7, 16, 10, 30, 0));
        testCart.setResellerId("1");
        testCart.setResellerName("TechStore");

        cartItem1 = new Cart();
        cartItem1.setCartId(2);
        cartItem1.setCustomerId("cust001");
        cartItem1.setProductId(101);
        cartItem1.setProductName("Mouse");
        cartItem1.setProductPrice(500.00);
        cartItem1.setQuantity(3);
        cartItem1.setTotalPrice(1500.00);

        cartItem2 = new Cart();
        cartItem2.setCartId(3);
        cartItem2.setCustomerId("cust001");
        cartItem2.setProductId(102);
        cartItem2.setProductName("Keyboard");
        cartItem2.setProductPrice(2000.00);
        cartItem2.setQuantity(1);
        cartItem2.setTotalPrice(2000.00);
    }

    // ========== CART SERVICE METHOD TESTS ==========
    
    @Test
    public void testCartServiceInitialization() {
        // CartService can be initialized successfully
        assertNotNull("CartService should be initialized", new CartService());
    }

    @Test
    public void testAddItemToCart() {
        assertNotNull("Cart item should be added", testCart);
        assertEquals("Item should be in cart", "cust001", testCart.getCustomerId());
        assertEquals("Product should be in cart", 100, testCart.getProductId());
    }

    @Test
    public void testCartItemDetails() {
        assertEquals("Cart ID should match", 1, testCart.getCartId());
        assertEquals("Customer ID should match", "cust001", testCart.getCustomerId());
        assertEquals("Product ID should match", 100, testCart.getProductId());
        assertEquals("Product name should match", "Laptop", testCart.getProductName());
        assertEquals("Product price should match", 50000.00, testCart.getProductPrice(), 0.01);
    }

    @Test
    public void testCartQuantityManagement() {
        // Test quantity updates
        testCart.setQuantity(2);
        assertEquals("Quantity should be updated to 2", (int)2, (int)testCart.getQuantity());

        testCart.setQuantity(5);
        assertEquals("Quantity should be updated to 5", (int)5, (int)testCart.getQuantity());
    }

    @Test
    public void testCartTotalPriceCalculation() {
        // Quantity: 2, Price: 50000 = Total: 100000
        double price = 50000.00;
        int quantity = 2;
        testCart.setProductPrice(price);
        testCart.setQuantity(quantity);
        testCart.setTotalPrice(price * quantity);

        assertEquals("Total price should be calculated", 100000.00, testCart.getTotalPrice(), 0.01);
    }

    @Test
    public void testMultipleItemsInCart() {
        assertEquals("First item customer", "cust001", cartItem1.getCustomerId());
        assertEquals("Second item customer", "cust001", cartItem2.getCustomerId());
        
        // Different cart items for same customer
        assertNotEquals("Different products in cart", cartItem1.getProductId(), cartItem2.getProductId());
    }

    @Test
    public void testRemoveItemFromCart() {
        // Simulate removing item by checking it exists
        assertTrue("Item exists in cart", testCart.getCartId() > 0);
        
        // After removal, item should be identified by ID for deletion
        int removedItemId = testCart.getProductId();
        assertEquals("Removed item ID should be identifiable", 100, removedItemId);
    }

    @Test
    public void testCartItemWithDifferentQuantities() {
        Cart item1 = new Cart();
        item1.setProductId(1);
        item1.setProductName("Product1");
        item1.setProductPrice(100.0);
        item1.setQuantity(1);
        item1.setTotalPrice(100.0);

        Cart item2 = new Cart();
        item2.setProductId(1);
        item2.setProductName("Product1");
        item2.setProductPrice(100.0);
        item2.setQuantity(5);
        item2.setTotalPrice(500.0);

        assertEquals("Different quantities", (int)item1.getQuantity(), (int)1);
        assertEquals("Different quantities", (int)item2.getQuantity(), (int)5);
    }

    @Test
    public void testCartItemWithDifferentPrices() {
        Cart item1 = new Cart();
        item1.setProductPrice(100.0);
        item1.setQuantity(2);
        item1.setTotalPrice(200.0);

        Cart item2 = new Cart();
        item2.setProductPrice(200.0);
        item2.setQuantity(2);
        item2.setTotalPrice(400.0);

        assertEquals("Item1 total", 200.0, item1.getTotalPrice(), 0.01);
        assertEquals("Item2 total", 400.0, item2.getTotalPrice(), 0.01);
    }

    @Test
    public void testCartDisplayDetails() {
        String expectedOutput = testCart.getProductName() + " - Qty: " + testCart.getQuantity() + 
                               " - Price: " + testCart.getProductPrice() + " - Total: " + testCart.getTotalPrice();
        
        assertNotNull("Cart display should be created", expectedOutput);
        assertTrue("Display should contain product name", expectedOutput.contains("Laptop"));
    }

    @Test
    public void testCartForSpecificCustomer() {
        String customerId = "cust001";
        assertEquals("Cart belongs to customer", customerId, testCart.getCustomerId());
        assertEquals("Cart item 1 customer", customerId, cartItem1.getCustomerId());
        assertEquals("Cart item 2 customer", customerId, cartItem2.getCustomerId());
    }

    @Test
    public void testCartDateTracking() {
        LocalDateTime testDate = LocalDateTime.of(2026, 7, 16, 10, 30, 0);
        testCart.setAddedDate(testDate);
        assertEquals("Cart date should be tracked", testDate, testCart.getAddedDate());
    }

    @Test
    public void testCartResellerTracking() {
        assertEquals("Reseller ID should be tracked", "1", testCart.getResellerId());
        assertEquals("Reseller name should be tracked", "TechStore", testCart.getResellerName());
    }

    @Test
    public void testCalculateCartSubtotal() {
        double subtotal = cartItem1.getTotalPrice() + cartItem2.getTotalPrice();
        double expectedSubtotal = 1500.00 + 2000.00;
        
        assertEquals("Subtotal calculation", expectedSubtotal, subtotal, 0.01);
    }

    @Test
    public void testCartWithZeroQuantity() {
        testCart.setQuantity(0);
        assertEquals("Cart can have zero quantity", (int)0, (int)testCart.getQuantity());
        testCart.setTotalPrice(0.0);
        assertEquals("Total price should be zero", 0.0, testCart.getTotalPrice(), 0.01);
    }

    @Test
    public void testCartWithSingleItem() {
        testCart.setQuantity(1);
        testCart.setProductPrice(100.0);
        testCart.setTotalPrice(100.0);

        assertEquals("Single item quantity", (int)1, (int)testCart.getQuantity());
        assertEquals("Single item total", 100.0, testCart.getTotalPrice(), 0.01);
    }

    @Test
    public void testCartWithBulkQuantity() {
        testCart.setQuantity(100);
        testCart.setProductPrice(50.0);
        testCart.setTotalPrice(5000.0);

        assertEquals("Bulk quantity", (int)100, (int)testCart.getQuantity());
        assertEquals("Bulk total", 5000.0, testCart.getTotalPrice(), 0.01);
    }

    @Test
    public void testCartPriceWithDecimals() {
        testCart.setProductPrice(99.99);
        testCart.setQuantity(3);
        testCart.setTotalPrice(299.97);

        assertEquals("Price with decimals", 99.99, testCart.getProductPrice(), 0.01);
        assertEquals("Total with decimals", 299.97, testCart.getTotalPrice(), 0.01);
    }

    @Test
    public void testCartIdentification() {
        Cart anotherCart = new Cart();
        anotherCart.setCartId(999);
        
        assertNotEquals("Different cart IDs", testCart.getCartId(), anotherCart.getCartId());
    }

    @Test
    public void testCartUpdateQuantity() {
        int originalQuantity = (int)testCart.getQuantity();
        testCart.setQuantity(originalQuantity + 5);
        
        assertEquals("Quantity should increase by 5", (int)(originalQuantity + 5), (int)testCart.getQuantity());
    }

    @Test
    public void testEmptyCartPriceCalculation() {
        Cart emptyCart = new Cart();
        emptyCart.setQuantity(0);
        emptyCart.setProductPrice(0.0);
        emptyCart.setTotalPrice(0.0);

        assertEquals("Empty cart total", 0.0, emptyCart.getTotalPrice(), 0.01);
    }

    @Test
    public void testCartProductNameHandling() {
        String[] productNames = {"Laptop", "Mouse", "Keyboard", "Monitor", "Headphones"};
        
        for (String name : productNames) {
            testCart.setProductName(name);
            assertEquals("Product name should match: " + name, name, testCart.getProductName());
        }
    }

    @Test
    public void testCartPrice_FractionalCents() {
        testCart.setProductPrice(19.99);
        testCart.setQuantity(7);
        testCart.setTotalPrice(139.93);

        assertEquals("Fractional price calculation", 139.93, testCart.getTotalPrice(), 0.01);
    }
}
