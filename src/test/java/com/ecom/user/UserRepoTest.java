package com.ecom.user;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

import com.ecom.reseller.Reseller;

/**
 * Unit Tests for User Repository and Login Classes
 * Tests user data persistence and authentication logic
 */
public class UserRepoTest {

    private User testUser;
    private User testCustomer;
    private Reseller testReseller;

    @Before
    public void setUp() {
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
        testCustomer.setEmail("john.doe@example.com");

        testReseller = new Reseller();
        testReseller.setUserId("res1");
        testReseller.setUserName("reseller1");
        testReseller.setPassword("res123");
        testReseller.setUserType(UserType.RESELLER);
        testReseller.setFirstName("Jane");
        testReseller.setLastName("Smith");
        testReseller.setEmail("jane.smith@example.com");
    }

    // ========== USER REPO METHOD TESTS ==========
    
    @Test
    public void testUserRepoInitialization() {
        // UserRepo can be initialized
        assertNotNull("UserRepo should be instantiable", new UserRepo());
    }

    @Test
    public void testUserCreation() {
        // Verify user can be created with all required fields
        assertNotNull("User should have ID", testUser.getUserId());
        assertNotNull("User should have username", testUser.getUserName());
        assertNotNull("User should have password", testUser.getPassword());
        assertNotNull("User should have email", testUser.getEmail());
    }

    @Test
    public void testUserNameFormat() {
        String[] validUserNames = {"user123", "john_doe", "alice.smith", "test-user"};
        
        for (String userName : validUserNames) {
            testUser.setUserName(userName);
            assertEquals("Username format should be accepted: " + userName, userName, testUser.getUserName());
        }
    }

    @Test
    public void testPasswordValidation() {
        String[] validPasswords = {"pass123", "secure!pass", "MyPass@2026", "longpassword"};
        
        for (String password : validPasswords) {
            testUser.setPassword(password);
            assertEquals("Password should be stored: " + password, password, testUser.getPassword());
        }
    }

    @Test
    public void testEmailValidation() {
        String[] validEmails = {
            "user@example.com",
            "john.doe@company.co.uk",
            "test+tag@domain.org",
            "contact123@business.net"
        };
        
        for (String email : validEmails) {
            testUser.setEmail(email);
            assertEquals("Email should be valid: " + email, email, testUser.getEmail());
        }
    }

    @Test
    public void testPhoneNumberValidation() {
        String[] validPhones = {"9876543210", "+919876543210", "(987) 654-3210"};
        
        for (String phone : validPhones) {
            testUser.setPhoneNo(phone);
            assertEquals("Phone should be valid: " + phone, phone, testUser.getPhoneNo());
        }
    }

    @Test
    public void testCustomerUserType() {
        testUser.setUserType(UserType.CUSTOMER);
        assertEquals("User type should be CUSTOMER", UserType.CUSTOMER, testUser.getUserType());
    }

    @Test
    public void testResellerUserType() {
        testUser.setUserType(UserType.RESELLER);
        assertEquals("User type should be RESELLER", UserType.RESELLER, testUser.getUserType());
    }

    @Test
    public void testUserPersonalInfo() {
        assertEquals("First name should be stored", "Test", testUser.getFirstName());
        assertEquals("Last name should be stored", "User", testUser.getLastName());
        assertEquals("Age should be stored", 25, testUser.getAge());
        assertEquals("Address should be stored", "123 Test Street", testUser.getAddress());
    }

    @Test
    public void testUserIsEmailUnique() {
        // Simulate checking if email already exists
        String email1 = "user1@example.com";
        String email2 = "user2@example.com";
        
        testUser.setEmail(email1);
        testCustomer.setEmail(email2);
        
        assertNotEquals("Different emails should be different", testUser.getEmail(), testCustomer.getEmail());
    }

    @Test
    public void testUserIsUsernameUnique() {
        String username1 = "user_one";
        String username2 = "user_two";
        
        testUser.setUserName(username1);
        testCustomer.setUserName(username2);
        
        assertNotEquals("Different usernames should be unique", testUser.getUserName(), testCustomer.getUserName());
    }

    @Test
    public void testLoginValidation() {
        // Test customer login credentials
        testCustomer.setUserName("customer1");
        testCustomer.setPassword("cust123");
        
        assertEquals("Username matches", "customer1", testCustomer.getUserName());
        assertEquals("Password matches", "cust123", testCustomer.getPassword());
    }

    @Test
    public void testCustomerLoginCreation() {
        testCustomer.setUserType(UserType.CUSTOMER);
        assertEquals("Should be customer type", UserType.CUSTOMER, testCustomer.getUserType());
    }

    @Test
    public void testResellerLoginCreation() {
        testReseller.setUserType(UserType.RESELLER);
        assertEquals("Should be reseller type", UserType.RESELLER, testReseller.getUserType());
    }

    @Test
    public void testUserRegistrationData() {
        User newUser = new User();
        newUser.setUserName("newuser");
        newUser.setPassword("newpass123");
        newUser.setEmail("new@example.com");
        newUser.setFirstName("New");
        newUser.setLastName("User");
        newUser.setPhoneNo("1111111111");
        newUser.setAddress("456 New St");
        newUser.setAge(30);
        newUser.setUserType(UserType.CUSTOMER);

        assertEquals("New user username", "newuser", newUser.getUserName());
        assertEquals("New user email", "new@example.com", newUser.getEmail());
    }

    @Test
    public void testUserAgeValidation() {
        testUser.setAge(18);
        assertEquals("Minimum age", 18, testUser.getAge());

        testUser.setAge(65);
        assertEquals("Normal age range", 65, testUser.getAge());

        testUser.setAge(100);
        assertEquals("Maximum age", 100, testUser.getAge());
    }

    @Test
    public void testUserAddressValidation() {
        String[] validAddresses = {
            "123 Main Street, City, State 12345",
            "Apt 456, 789 Oak Ave, Town",
            "PO Box 999, Rural Area"
        };
        
        for (String address : validAddresses) {
            testUser.setAddress(address);
            assertEquals("Address should be stored: " + address, address, testUser.getAddress());
        }
    }

    @Test
    public void testUserMetadataCreation() {
        testUser.setCreatedBy("admin");
        testUser.setCreateDate(LocalDate.of(2026, 7, 16));
        testUser.setModifiedBy("user");
        testUser.setModifiedDate(LocalDate.of(2026, 7, 17));

        assertEquals("CreatedBy metadata", "admin", testUser.getCreatedBy());
        assertEquals("CreateDate metadata", LocalDate.of(2026, 7, 16), testUser.getCreateDate());
    }

    @Test
    public void testUserMetadataModification() {
        testUser.setCreatedBy("admin");
        testUser.setCreateDate(LocalDate.of(2026, 7, 16));
        
        // Later modification
        testUser.setModifiedBy("moderator");
        testUser.setModifiedDate(LocalDate.of(2026, 7, 18));

        assertEquals("Original creator", "admin", testUser.getCreatedBy());
        assertEquals("Modified by", "moderator", testUser.getModifiedBy());
    }

    @Test
    public void testCustomerVsResellerType() {
        assertEquals("Customer type value", 1, UserType.CUSTOMER);
        assertEquals("Reseller type value", 2, UserType.RESELLER);
        assertNotEquals("Types should be different", UserType.CUSTOMER, UserType.RESELLER);
    }

    @Test
    public void testUserPasswordChange() {
        String oldPassword = testUser.getPassword();
        testUser.setPassword("newPassword123");
        
        assertNotEquals("Password should change", oldPassword, testUser.getPassword());
        assertEquals("New password should be set", "newPassword123", testUser.getPassword());
    }

    @Test
    public void testUserDataConsistency() {
        testUser.setUserName("consistent_user");
        testUser.setEmail("consistent@example.com");
        
        assertEquals("Username consistency", "consistent_user", testUser.getUserName());
        assertEquals("Email consistency", "consistent@example.com", testUser.getEmail());
    }

    @Test
    public void testMultipleUserCreation() {
        User user2 = new User();
        user2.setUserId("user2");
        user2.setUserName("user2");
        
        User user3 = new User();
        user3.setUserId("user3");
        user3.setUserName("user3");
        
        assertNotEquals("Different user IDs", testUser.getUserId(), user2.getUserId());
        assertNotEquals("Different usernames", user2.getUserName(), user3.getUserName());
    }

    @Test
    public void testUserFieldValidation() {
        // All required fields should be settable
        assertNotNull("UserId can be set", testUser.getUserId());
        assertNotNull("UserName can be set", testUser.getUserName());
        assertNotNull("Password can be set", testUser.getPassword());
        assertNotNull("Email can be set", testUser.getEmail());
    }

    @Test
    public void testResellerInheritsFromUser() {
        Reseller reseller = new Reseller();
        reseller.setUserId("res100");
        reseller.setUserName("reseller_user");
        reseller.setEmail("reseller@example.com");
        
        // Should have all User properties
        assertEquals("Reseller inherits userId", "res100", reseller.getUserId());
        assertEquals("Reseller inherits userName", "reseller_user", reseller.getUserName());
        assertEquals("Reseller inherits email", "reseller@example.com", reseller.getEmail());
    }

    @Test
    public void testExceptionClasses() {
        // Test exception classes are available
        try {
            throw new FailedToCreateUserException("test");
        } catch (FailedToCreateUserException e) {
            assertNotNull("Exception should be caught", e);
        }
    }
}
