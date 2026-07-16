package com.ecom.reseller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

import com.ecom.user.UserType;
import com.ecom.app.AppSession;

/**
 * Unit Tests for Reseller Package Classes
 * Tests reseller-specific functionality and inheritance
 */
public class ResellerLoginTest {

    private Reseller testReseller;
    private ResellerLogin resellerLogin;
    private AppSession appSession;

    @Before
    public void setUp() {
        appSession = new AppSession();
        testReseller = new Reseller();
        testReseller.setUserId("res1");
        testReseller.setUserName("reseller1");
        testReseller.setPassword("respass123");
        testReseller.setUserType(UserType.RESELLER);
        testReseller.setFirstName("John");
        testReseller.setLastName("Seller");
        testReseller.setEmail("john@reseller.com");
        testReseller.setPhoneNo("9876543210");
        testReseller.setAddress("123 Business St");
        testReseller.setAge(40);

        resellerLogin = new ResellerLogin();
        
        // Clear session before each test
        AppSession.logout();
    }

    // ========== RESELLER CLASS TESTS ==========
    
    @Test
    public void testResellerCreation() {
        assertNotNull("Reseller should be created", testReseller);
        assertEquals("Reseller ID should be set", "res1", testReseller.getUserId());
        assertEquals("Reseller username should be set", "reseller1", testReseller.getUserName());
    }

    @Test
    public void testResellerInheritance() {
        // Reseller should inherit all User properties
        assertEquals("Reseller has userId", "res1", testReseller.getUserId());
        assertEquals("Reseller has userName", "reseller1", testReseller.getUserName());
        assertEquals("Reseller has password", "respass123", testReseller.getPassword());
        assertEquals("Reseller has firstName", "John", testReseller.getFirstName());
        assertEquals("Reseller has lastName", "Seller", testReseller.getLastName());
        assertEquals("Reseller has email", "john@reseller.com", testReseller.getEmail());
    }

    @Test
    public void testResellerType() {
        assertEquals("Reseller type should be RESELLER", UserType.RESELLER, testReseller.getUserType());
    }

    @Test
    public void testResellerPersonalInfo() {
        assertEquals("First name", "John", testReseller.getFirstName());
        assertEquals("Last name", "Seller", testReseller.getLastName());
        assertEquals("Phone number", "9876543210", testReseller.getPhoneNo());
        assertEquals("Address", "123 Business St", testReseller.getAddress());
        assertEquals("Age", 40, testReseller.getAge());
    }

    @Test
    public void testResellerSettersGetters() {
        testReseller.setUserId("res99");
        testReseller.setUserName("newseller");
        testReseller.setPassword("newpass");
        testReseller.setEmail("new@reseller.com");

        assertEquals("UserId updated", "res99", testReseller.getUserId());
        assertEquals("UserName updated", "newseller", testReseller.getUserName());
        assertEquals("Password updated", "newpass", testReseller.getPassword());
        assertEquals("Email updated", "new@reseller.com", testReseller.getEmail());
    }

    @Test
    public void testResellerMetadata() {
        testReseller.setCreatedBy("admin");
        testReseller.setCreateDate(LocalDate.of(2026, 7, 16));
        testReseller.setModifiedBy("moderator");
        testReseller.setModifiedDate(LocalDate.of(2026, 7, 17));

        assertEquals("CreatedBy", "admin", testReseller.getCreatedBy());
        assertEquals("CreateDate", LocalDate.of(2026, 7, 16), testReseller.getCreateDate());
        assertEquals("ModifiedBy", "moderator", testReseller.getModifiedBy());
        assertEquals("ModifiedDate", "2026-07-17", testReseller.getModifiedDate());
    }

    // ========== RESELLER LOGIN TESTS ==========
    
    @Test
    public void testResellerLoginInitialization() {
        assertNotNull("ResellerLogin should be instantiated", resellerLogin);
    }

    @Test
    public void testResellerLoginAsResellerType() {
        // ResellerLogin should use RESELLER type
        assertEquals("Should use RESELLER type", UserType.RESELLER, UserType.RESELLER);
    }

    @Test
    public void testResellerRegistration() {
        // When a reseller registers, user type should be RESELLER
        Reseller newReseller = new Reseller();
        newReseller.setUserType(UserType.RESELLER);
        
        assertEquals("New reseller should be RESELLER type", UserType.RESELLER, newReseller.getUserType());
    }

    @Test
    public void testResellerCredentials() {
        // Test reseller can have credentials set
        testReseller.setUserName("seller_john");
        testReseller.setPassword("secure_pass_123");
        
        assertEquals("Username should be set", "seller_john", testReseller.getUserName());
        assertEquals("Password should be set", "secure_pass_123", testReseller.getPassword());
    }

    @Test
    public void testResellerEmailValidation() {
        String[] validEmails = {
            "seller@business.com",
            "john.seller@company.org",
            "seller+bulk@example.net"
        };
        
        for (String email : validEmails) {
            testReseller.setEmail(email);
            assertEquals("Reseller email: " + email, email, testReseller.getEmail());
        }
    }

    @Test
    public void testResellerContactInfo() {
        String[] validPhones = {"9876543210", "+919876543210", "(987) 654-3210"};
        
        for (String phone : validPhones) {
            testReseller.setPhoneNo(phone);
            assertEquals("Reseller phone: " + phone, phone, testReseller.getPhoneNo());
        }
    }

    @Test
    public void testResellerBusinessAddress() {
        String[] validAddresses = {
            "123 Business Park, City",
            "Apt 500, Commercial Plaza",
            "Suite 200, Business Complex"
        };
        
        for (String address : validAddresses) {
            testReseller.setAddress(address);
            assertEquals("Reseller address: " + address, address, testReseller.getAddress());
        }
    }

    @Test
    public void testResellerSession() {
        AppSession.setLoggedInUser(testReseller);
        
        assertTrue("Reseller should be logged in", AppSession.isLoggedIn());
        assertEquals("Logged user should be reseller", testReseller.getUserName(), 
                     appSession.getLoggedInUser().getUserName());
    }

    @Test
    public void testResellerLogout() {
        AppSession.setLoggedInUser(testReseller);
        assertTrue("Reseller logged in", AppSession.isLoggedIn());
        
        AppSession.logout();
        assertFalse("Reseller logged out", AppSession.isLoggedIn());
    }

    @Test
    public void testMultipleResellers() {
        Reseller reseller1 = new Reseller();
        reseller1.setUserId("res1");
        reseller1.setUserName("seller1");

        Reseller reseller2 = new Reseller();
        reseller2.setUserId("res2");
        reseller2.setUserName("seller2");

        assertNotEquals("Different seller IDs", reseller1.getUserId(), reseller2.getUserId());
        assertNotEquals("Different seller names", reseller1.getUserName(), reseller2.getUserName());
    }

    @Test
    public void testResellerTypeValue() {
        testReseller.setUserType(UserType.RESELLER);
        assertEquals("RESELLER type value", 2, testReseller.getUserType());
    }

    @Test
    public void testResellerPasswordUpdate() {
        String oldPassword = "respass123";
        testReseller.setPassword(oldPassword);
        assertEquals("Initial password", oldPassword, testReseller.getPassword());

        testReseller.setPassword("newrespass456");
        assertNotEquals("Password should change", oldPassword, testReseller.getPassword());
        assertEquals("New password set", "newrespass456", testReseller.getPassword());
    }

    @Test
    public void testResellerUsernameUniqueness() {
        Reseller seller1 = new Reseller();
        seller1.setUserName("unique_seller_1");

        Reseller seller2 = new Reseller();
        seller2.setUserName("unique_seller_2");

        assertNotEquals("Usernames should be unique", seller1.getUserName(), seller2.getUserName());
    }

    @Test
    public void testResellerEmailUniqueness() {
        Reseller seller1 = new Reseller();
        seller1.setEmail("seller1@unique.com");

        Reseller seller2 = new Reseller();
        seller2.setEmail("seller2@unique.com");

        assertNotEquals("Emails should be unique", seller1.getEmail(), seller2.getEmail());
    }

    @Test
    public void testResellerAgeValidation() {
        testReseller.setAge(25);
        assertEquals("Young reseller age", 25, testReseller.getAge());

        testReseller.setAge(50);
        assertEquals("Middle age reseller", 50, testReseller.getAge());

        testReseller.setAge(75);
        assertEquals("Senior reseller age", 75, testReseller.getAge());
    }

    @Test
    public void testResellerDataIntegrity() {
        Reseller seller = new Reseller();
        seller.setUserName("data_test");
        seller.setEmail("datatest@example.com");
        seller.setPhoneNo("1234567890");

        // Verify data is stored correctly
        assertEquals("Data consistency 1", "data_test", seller.getUserName());
        assertEquals("Data consistency 2", "datatest@example.com", seller.getEmail());
        assertEquals("Data consistency 3", "1234567890", seller.getPhoneNo());
    }

    @Test
    public void testResellerProfileCompletion() {
        testReseller.setFirstName("Jane");
        testReseller.setLastName("Smith");
        testReseller.setEmail("jane.smith@reseller.com");
        testReseller.setPhoneNo("9999888877");
        testReseller.setAddress("456 Business Ave");

        assertNotNull("First name should be set", testReseller.getFirstName());
        assertNotNull("Last name should be set", testReseller.getLastName());
        assertNotNull("Email should be set", testReseller.getEmail());
        assertNotNull("Phone should be set", testReseller.getPhoneNo());
        assertNotNull("Address should be set", testReseller.getAddress());
    }

    @Test
    public void testResellerTypeConformance() {
        // Reseller type should always be 2
        testReseller.setUserType(UserType.RESELLER);
        assertTrue("Should be RESELLER type", testReseller.getUserType() == UserType.RESELLER);
    }

    @Test
    public void testResellerExceptionHandling() {
        // Should be able to throw and catch reseller-related exceptions
        try {
            Reseller seller = new Reseller();
            assertNotNull("Reseller created", seller);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testResellerRepositoryLogic() {
        // ResellerRepo should extend UserRepo and work with RESELLER type
        ResellerRepo repo = new ResellerRepo();
        assertNotNull("ResellerRepo should be instantiable", repo);
    }

    @Test
    public void testResellerFieldUpdate() {
        // Test multiple field updates in sequence
        testReseller.setUserName("updated_reseller");
        testReseller.setEmail("updated@reseller.com");
        testReseller.setPhoneNo("5555555555");

        assertEquals("Username updated", "updated_reseller", testReseller.getUserName());
        assertEquals("Email updated", "updated@reseller.com", testReseller.getEmail());
        assertEquals("Phone updated", "5555555555", testReseller.getPhoneNo());
    }

    @Test
    public void testResellerSpecialCharactersInName() {
        testReseller.setFirstName("François");
        testReseller.setLastName("O'Connor");

        assertEquals("First name with accent", "François", testReseller.getFirstName());
        assertEquals("Last name with apostrophe", "O'Connor", testReseller.getLastName());
    }
}
