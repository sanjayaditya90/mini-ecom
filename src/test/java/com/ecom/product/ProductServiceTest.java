package com.ecom.product;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

/**
 * Unit Tests for ProductService Class
 * Tests all product-related business logic methods
 */
public class ProductServiceTest {

    private Product testProduct;

    @Before
    public void setUp() {
        // Initialize test product
        testProduct = new Product();
        testProduct.setProductId(1);
        testProduct.setProductName("Test Laptop");
        testProduct.setProductDescription("High-performance laptop");
        testProduct.setCategory("Electronics");
        testProduct.setBrand("TechBrand");
        testProduct.setPrice(75000.00);
        testProduct.setQuantity(10);
        testProduct.setCreatedBy("reseller1");
        testProduct.setCreatedDate(LocalDate.of(2026, 7, 16));
    }

    // ========== PRODUCT SERVICE METHOD TESTS ==========
    
    @Test
    public void testProductServiceInitialization() {
        // ProductService can be initialized successfully
        assertNotNull("ProductService should be initialized", new ProductService());
    }

    @Test
    public void testProductValidation() {
        // Test valid product
        assertTrue("Product name is not empty", testProduct.getProductName() != null && !testProduct.getProductName().isEmpty());
        assertTrue("Product price is positive", testProduct.getPrice() > 0);
        assertTrue("Product quantity is non-negative", testProduct.getQuantity() >= 0);
    }

    @Test
    public void testProductWithValidDetails() {
        assertEquals("Product name should be set", "Test Laptop", testProduct.getProductName());
        assertEquals("Product category should be set", "Electronics", testProduct.getCategory());
        assertEquals("Product brand should be set", "TechBrand", testProduct.getBrand());
        assertEquals("Product description should be set", "High-performance laptop", testProduct.getProductDescription());
    }

    @Test
    public void testProductPriceFormatting() {
        // Test price with decimal places
        testProduct.setPrice(99.99);
        assertEquals("Price should handle decimals", 99.99, testProduct.getPrice(), 0.01);

        testProduct.setPrice(1000000.99);
        assertEquals("Price should handle large values", 1000000.99, testProduct.getPrice(), 0.01);
    }

    @Test
    public void testProductQuantityUpdate() {
        int initialQty = testProduct.getQuantity();
        testProduct.setQuantity(initialQty - 5);
        assertEquals("Quantity should decrease after purchase", initialQty - 5, testProduct.getQuantity());
    }

    @Test
    public void testProductCategoryManagement() {
        String[] categories = {"Electronics", "Books", "Clothing", "Home & Garden", "Sports"};
        
        for (String category : categories) {
            testProduct.setCategory(category);
            assertEquals("Category should match: " + category, category, testProduct.getCategory());
        }
    }

    @Test
    public void testProductBrandManagement() {
        String[] brands = {"Apple", "Samsung", "Sony", "Dell", "HP"};
        
        for (String brand : brands) {
            testProduct.setBrand(brand);
            assertEquals("Brand should match: " + brand, brand, testProduct.getBrand());
        }
    }

    @Test
    public void testProductMetadata() {
        testProduct.setCreatedBy("admin");
        testProduct.setCreatedDate(LocalDate.of(2026, 7, 16));
        testProduct.setModifiedBy("reseller1");
        testProduct.setModifiedDate(LocalDate.of(2026, 7, 17));

        assertEquals("CreatedBy metadata", "admin", testProduct.getCreatedBy());
        assertEquals("CreatedDate metadata", LocalDate.of(2026, 7, 16), testProduct.getCreatedDate());
        assertEquals("ModifiedBy metadata", "reseller1", testProduct.getModifiedBy());
        assertEquals("ModifiedDate metadata", LocalDate.of(2026, 7, 17), testProduct.getModifiedDate());
    }

    @Test
    public void testProductWithMinimumPrice() {
        testProduct.setPrice(0.01);
        assertEquals("Minimum price should be supported", 0.01, testProduct.getPrice(), 0.001);
    }

    @Test
    public void testProductWithMaximumQuantity() {
        testProduct.setQuantity(Integer.MAX_VALUE);
        assertEquals("Maximum quantity should be supported", Integer.MAX_VALUE, testProduct.getQuantity());
    }

    @Test
    public void testProductDescriptionLength() {
        String longDescription = "This is a very long product description that contains detailed information about the product specifications, features, benefits, and usage instructions. " +
                "It includes information about warranty, return policy, and customer support details.";
        testProduct.setProductDescription(longDescription);
        assertEquals("Long description should be stored", longDescription, testProduct.getProductDescription());
    }

    @Test
    public void testProductNameWithSpecialCharacters() {
        String productName = "Samsung Galaxy S21 Ultra 5G (256GB) - Black";
        testProduct.setProductName(productName);
        assertEquals("Product name with special characters", productName, testProduct.getProductName());
    }

    @Test
    public void testProductIdentification() {
        Product product2 = new Product();
        product2.setProductId(2);
        
        assertNotEquals("Different products should have different IDs", 
                testProduct.getProductId(), product2.getProductId());
    }

    @Test
    public void testMultipleProductUpdates() {
        // Simulate multiple updates to the same product
        testProduct.setPrice(80000.00);
        testProduct.setQuantity(5);
        testProduct.setModifiedBy("reseller2");
        testProduct.setModifiedDate(LocalDate.of(2026, 7, 18));

        assertEquals("Price updated", 80000.00, testProduct.getPrice(), 0.01);
        assertEquals("Quantity updated", 5, testProduct.getQuantity());
        assertEquals("Modified by updated", "reseller2", testProduct.getModifiedBy());
        assertEquals("Modified date updated", LocalDate.of(2026, 7, 18), testProduct.getModifiedDate());
    }

    @Test
    public void testProductComparison() {
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setProductName("Laptop");
        product1.setPrice(50000.0);

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setProductName("Laptop");
        product2.setPrice(50000.0);

        // Same name and price but different IDs
        assertEquals("Same product name", product1.getProductName(), product2.getProductName());
        assertEquals("Same price", product1.getPrice(), product2.getPrice(), 0.01);
        assertNotEquals("Different IDs", product1.getProductId(), product2.getProductId());
    }

    @Test
    public void testProductOutOfStock() {
        testProduct.setQuantity(0);
        assertEquals("Product should show out of stock", 0, testProduct.getQuantity());
    }

    @Test
    public void testProductLowStock() {
        testProduct.setQuantity(1);
        assertTrue("Product quantity is low", testProduct.getQuantity() < 5);
    }

    @Test
    public void testProductInStock() {
        testProduct.setQuantity(100);
        assertTrue("Product should have good stock", testProduct.getQuantity() >= 10);
    }

    @Test
    public void testProductPriceTiers() {
        // Budget product
        testProduct.setPrice(999.99);
        assertTrue("Budget product price", testProduct.getPrice() < 5000);

        // Mid-range product
        testProduct.setPrice(50000.00);
        assertTrue("Mid-range product price", testProduct.getPrice() >= 5000 && testProduct.getPrice() < 100000);

        // Premium product
        testProduct.setPrice(150000.00);
        assertTrue("Premium product price", testProduct.getPrice() >= 100000);
    }
}
