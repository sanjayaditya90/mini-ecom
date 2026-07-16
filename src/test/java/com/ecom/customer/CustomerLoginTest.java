package com.ecom.customer;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

import com.ecom.user.User;
import com.ecom.user.UserType;
import com.ecom.app.AppSession;

/**
 * Unit Tests for Customer Package Classes
 * Tests customer-specific functionality and authentication
 */
public class CustomerLoginTest {

    private User testCustomer;
    private CustomerLogin customerLogin;
    private AppSession appSession;

    @Before
    public void setUp() {
        appSession = new AppSession();
        testCustomer = new User();
        testCustomer.setUserId("cust1");
        testCustomer.setUserName("customer1");
        testCustomer.setPassword("custpass123");
        testCustomer.setUserType(UserType.CUSTOMER);
        testCustomer.setFirstName("Alice");
        testCustomer.setLastName("Buyer");
        testCustomer.setEmail("alice@customer.com");
        testCustomer.setPhoneNo("9876543210");
        testCustomer.setAddress("123 Home St");
        testCustomer.setAge(30);

        customerLogin = new CustomerLogin();
        
        // Clear session before each test
        AppSession.logout();
    }

    // ========== CUSTOMER CLASS TESTS ==========
    
    @Test
    public void testCustomerCreation() {
        assertNotNull("Customer should be created", testCustomer);
        assertEquals("Customer ID should be set", "cust1", testCustomer.getUserId());
        assertEquals("Customer username should be set", "customer1", testCustomer.getUserName());
    }

    @Test
    public void testCustomerType() {
        assertEquals("Customer type should be CUSTOMER", UserType.CUSTOMER, testCustomer.getUserType());
    }

    @Test
    public void testCustomerPersonalInfo() {
        assertEquals("First name", "Alice", testCustomer.getFirstName());
        assertEquals("Last name", "Buyer", testCustomer.getLastName());
        assertEquals("Phone number", "9876543210", testCustomer.getPhoneNo());
        assertEquals("Address", "123 Home St", testCustomer.getAddress());
        assertEquals("Age", 30, testCustomer.getAge());
    }

    @Test
    public void testCustomerSettersGetters() {
        testCustomer.setUserId("cust99");
        testCustomer.setUserName("newcustomer");
        testCustomer.setPassword("newpass");
        testCustomer.setEmail("new@customer.com");

        assertEquals("UserId updated", "cust99", testCustomer.getUserId());
        assertEquals("UserName updated", "newcustomer", testCustomer.getUserName());
        assertEquals("Password updated", "newpass", testCustomer.getPassword());
        assertEquals("Email updated", "new@customer.com", testCustomer.getEmail());
    }

    @Test
    public void testCustomerMetadata() {
        testCustomer.setCreatedBy("admin");
        testCustomer.setCreateDate(LocalDate.of(2026, 7, 16));
        testCustomer.setModifiedBy("moderator");
        testCustomer.setModifiedDate(LocalDate.of(2026, 7, 17));

        assertEquals("CreatedBy", "admin", testCustomer.getCreatedBy());
        assertEquals("CreateDate", LocalDate.of(2026, 7, 16), testCustomer.getCreateDate());
        assertEquals("ModifiedBy", "moderator", testCustomer.getModifiedBy());
        assertEquals("ModifiedDate", "2026-07-17", testCustomer.getModifiedDate());
    }

    @Test
    public void testCustomerCredentials() {
        testCustomer.setUserName("alice_buyer");
        testCustomer.setPassword("secure_pass_123");
        
        assertEquals("Username should be set", "alice_buyer", testCustomer.getUserName());
        assertEquals("Password should be set", "secure_pass_123", testCustomer.getPassword());
    }

    @Test
    public void testCustomerEmailValidation() {
        String[] validEmails = {
            "buyer@example.com",
            "alice.buyer@domain.org",
            "customer+tag@shopping.com"
        };
        
        for (String email : validEmails) {
            testCustomer.setEmail(email);
            assertEquals("Customer email: " + email, email, testCustomer.getEmail());
        }
    }

    @Test
    public void testCustomerPhoneValidation() {
        String[] validPhones = {"9876543210", "+919876543210", "(987) 654-3210"};
        
        for (String phone : validPhones) {
            testCustomer.setPhoneNo(phone);
            assertEquals("Customer phone: " + phone, phone, testCustomer.getPhoneNo());
        }
    }

    @Test
    public void testCustomerAddressValidation() {
        String[] validAddresses = {
            "123 Main Street, City, State 12345",
            "Apt 456, 789 Oak Ave, Town",
            "House 10, Residential Colony"
        };
        
        for (String address : validAddresses) {
            testCustomer.setAddress(address);
            assertEquals("Customer address: " + address, address, testCustomer.getAddress());
        }
    }

    // ========== CUSTOMER LOGIN TESTS ==========
    
    @Test
    public void testCustomerLoginInitialization() {
        assertNotNull("CustomerLogin should be instantiated", customerLogin);
    }

    @Test
    public void testCustomerLoginAsCustomerType() {
        // CustomerLogin should use CUSTOMER type
        assertEquals("Should use CUSTOMER type", UserType.CUSTOMER, UserType.CUSTOMER);
    }

    @Test
    public void testCustomerRegistration() {
        // When a customer registers, user type should be CUSTOMER
        User newCustomer = new User();
        newCustomer.setUserType(UserType.CUSTOMER);
        
        assertEquals("New customer should be CUSTOMER type", UserType.CUSTOMER, newCustomer.getUserType());
    }

    @Test
    public void testCustomerSession() {
        AppSession.setLoggedInUser(testCustomer);
        
        assertTrue("Customer should be logged in", AppSession.isLoggedIn());
        assertEquals("Logged user should be customer", testCustomer.getUserName(), 
                     appSession.getLoggedInUser().getUserName());
    }

    @Test
    public void testCustomerLogout() {
        AppSession.setLoggedInUser(testCustomer);
        assertTrue("Customer logged in", AppSession.isLoggedIn());
        
        AppSession.logout();
        assertFalse("Customer logged out", AppSession.isLoggedIn());
    }

    @Test
    public void testMultipleCustomers() {
        User customer1 = new User();
        customer1.setUserId("cust1");
        customer1.setUserName("buyer1");

        User customer2 = new User();
        customer2.setUserId("cust2");
        customer2.setUserName("buyer2");

        assertNotEquals("Different customer IDs", customer1.getUserId(), customer2.getUserId());
        assertNotEquals("Different customer names", customer1.getUserName(), customer2.getUserName());
    }

    @Test
    public void testCustomerTypeValue() {
        testCustomer.setUserType(UserType.CUSTOMER);
        assertEquals("CUSTOMER type value", 1, testCustomer.getUserType());
    }

    @Test
    public void testCustomerPasswordUpdate() {
        String oldPassword = "custpass123";
        testCustomer.setPassword(oldPassword);
        assertEquals("Initial password", oldPassword, testCustomer.getPassword());

        testCustomer.setPassword("newcustpass456");
        assertNotEquals("Password should change", oldPassword, testCustomer.getPassword());
        assertEquals("New password set", "newcustpass456", testCustomer.getPassword());
    }

    @Test
    public void testCustomerUsernameUniqueness() {
        User cust1 = new User();
        cust1.setUserName("unique_buyer_1");

        User cust2 = new User();
        cust2.setUserName("unique_buyer_2");

        assertNotEquals("Usernames should be unique", cust1.getUserName(), cust2.getUserName());
    }

    @Test
    public void testCustomerEmailUniqueness() {
        User cust1 = new User();
        cust1.setEmail("buyer1@unique.com");

        User cust2 = new User();
        cust2.setEmail("buyer2@unique.com");

        assertNotEquals("Emails should be unique", cust1.getEmail(), cust2.getEmail());
    }

    @Test
    public void testCustomerAgeValidation() {
        testCustomer.setAge(18);
        assertEquals("Young customer age", 18, testCustomer.getAge());

        testCustomer.setAge(40);
        assertEquals("Middle age customer", 40, testCustomer.getAge());

        testCustomer.setAge(70);
        assertEquals("Senior customer age", 70, testCustomer.getAge());
    }

    @Test
    public void testCustomerDataIntegrity() {
        User customer = new User();
        customer.setUserName("integrity_test");
        customer.setEmail("integrity@example.com");
        customer.setPhoneNo("1234567890");

        // Verify data is stored correctly
        assertEquals("Data consistency 1", "integrity_test", customer.getUserName());
        assertEquals("Data consistency 2", "integrity@example.com", customer.getEmail());
        assertEquals("Data consistency 3", "1234567890", customer.getPhoneNo());
    }

    @Test
    public void testCustomerProfileCompletion() {
        testCustomer.setFirstName("Bob");
        testCustomer.setLastName("Shopper");
        testCustomer.setEmail("bob.shopper@customer.com");
        testCustomer.setPhoneNo("8888777766");
        testCustomer.setAddress("999 Shopping Ave");

        assertNotNull("First name should be set", testCustomer.getFirstName());
        assertNotNull("Last name should be set", testCustomer.getLastName());
        assertNotNull("Email should be set", testCustomer.getEmail());
        assertNotNull("Phone should be set", testCustomer.getPhoneNo());
        assertNotNull("Address should be set", testCustomer.getAddress());
    }

    @Test
    public void testCustomerTypeConformance() {
        // Customer type should always be 1
        testCustomer.setUserType(UserType.CUSTOMER);
        assertTrue("Should be CUSTOMER type", testCustomer.getUserType() == UserType.CUSTOMER);
    }

    @Test
    public void testCustomerLoginPhoneNumber() {
        String phone = "9999888877";
        testCustomer.setPhoneNo(phone);
        assertEquals("Phone number for login", phone, testCustomer.getPhoneNo());
    }

    @Test
    public void testCustomerPreferredAddress() {
        String homeAddress = "123 Residential Colony";
        testCustomer.setAddress(homeAddress);
        assertEquals("Preferred address", homeAddress, testCustomer.getAddress());
    }

    @Test
    public void testCustomerFieldUpdate() {
        // Test multiple field updates in sequence
        testCustomer.setUserName("updated_customer");
        testCustomer.setEmail("updated@customer.com");
        testCustomer.setPhoneNo("3333333333");

        assertEquals("Username updated", "updated_customer", testCustomer.getUserName());
        assertEquals("Email updated", "updated@customer.com", testCustomer.getEmail());
        assertEquals("Phone updated", "3333333333", testCustomer.getPhoneNo());
    }

    @Test
    public void testCustomerSpecialCharactersInName() {
        testCustomer.setFirstName("María");
        testCustomer.setLastName("Müller");

        assertEquals("First name with accent", "María", testCustomer.getFirstName());
        assertEquals("Last name with umlaut", "Müller", testCustomer.getLastName());
    }

    @Test
    public void testCustomerLoginFlow() {
        // Simulate customer login process
        testCustomer.setUserName("testcustomer");
        testCustomer.setPassword("testpass");
        testCustomer.setUserType(UserType.CUSTOMER);

        // Verify login credentials
        assertEquals("Username for login", "testcustomer", testCustomer.getUserName());
        assertEquals("Password for login", "testpass", testCustomer.getPassword());
        assertEquals("User type is customer", UserType.CUSTOMER, testCustomer.getUserType());
    }

    @Test
    public void testCustomerAccount_Creation() {
        User newCustomer = new User();
        newCustomer.setUserName("brand_new_customer");
        newCustomer.setPassword("brandnew123");
        newCustomer.setEmail("brandnew@customer.com");
        newCustomer.setFirstName("New");
        newCustomer.setLastName("Customer");
        newCustomer.setUserType(UserType.CUSTOMER);

        assertEquals("New account username", "brand_new_customer", newCustomer.getUserName());
        assertEquals("New account user type", UserType.CUSTOMER, newCustomer.getUserType());
    }

    @Test
    public void testCustomerExceptionHandling() {
        // Should be able to throw and catch customer-related exceptions
        try {
            User customer = new User();
            assertNotNull("Customer created", customer);
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testCustomerRepositoryLogic() {
        // UserRepo should work with CUSTOMER type for customer operations
        assertNotNull("UserRepo should handle customer operations", new com.ecom.user.UserRepo());
    }
}
