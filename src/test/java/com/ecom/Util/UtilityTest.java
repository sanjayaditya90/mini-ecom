package com.ecom.Util;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.ecom.app.AppSession;
import com.ecom.user.User;
import com.ecom.user.UserType;
import com.ecom.reseller.Reseller;

/**
 * Unit Tests for Utility Class
 * Tests utility and helper methods used across the application
 */
public class UtilityTest {

    private User testCustomer;
    private User testReseller;
    private Utility utility;

    @Before
    public void setUp() {
        // Clear session before each test
        AppSession.logout();
        
        // Initialize utility
        utility = new Utility();

        // Setup test customer
        testCustomer = new User();
        testCustomer.setUserId("cust1");
        testCustomer.setUserName("customer1");
        testCustomer.setUserType(UserType.CUSTOMER);
        testCustomer.setEmail("customer@example.com");

        // Setup test reseller
        testReseller = new Reseller();
        testReseller.setUserId("res1");
        testReseller.setUserName("reseller1");
        testReseller.setUserType(UserType.RESELLER);
        testReseller.setEmail("reseller@example.com");
    }

    // ========== UTILITY METHOD TESTS ==========
    
    @Test
    public void testUtilityClassInitialization() {
        // Utility class can be instantiated
        assertNotNull("Utility class should be instantiable", new Utility());
    }

    @Test
    public void testEnsureLoggedInWithCustomer() {
        // Customer is logged in and user type matches
        AppSession.setLoggedInUser(testCustomer);
        boolean result = utility.ensureLoggedIn(UserType.CUSTOMER);
        
        assertTrue("Customer should be logged in with CUSTOMER type", result);
    }

    @Test
    public void testEnsureLoggedInWithReseller() {
        // Reseller is logged in and user type matches
        AppSession.setLoggedInUser(testReseller);
        boolean result = utility.ensureLoggedIn(UserType.RESELLER);
        
        assertTrue("Reseller should be logged in with RESELLER type", result);
    }

    @Test
    public void testEnsureLoggedInWithoutLogin() {
        // No user logged in
        boolean result = utility.ensureLoggedIn(UserType.CUSTOMER);
        
        assertFalse("Should not be logged in", result);
    }

    @Test
    public void testEnsureLoggedInWithMismatchedType() {
        // Customer logged in but checking for RESELLER type
        AppSession.setLoggedInUser(testCustomer);
        boolean result = utility.ensureLoggedIn(UserType.RESELLER);
        
        assertFalse("Type mismatch should return false", result);
    }

    @Test
    public void testEnsureLoggedInResellerCheckingCustomer() {
        // Reseller logged in but checking for CUSTOMER type
        AppSession.setLoggedInUser(testReseller);
        boolean result = utility.ensureLoggedIn(UserType.CUSTOMER);
        
        assertFalse("Reseller should not pass customer check", result);
    }

    @Test
    public void testValidUserTypeConstants() {
        assertEquals("CUSTOMER type constant", 1, UserType.CUSTOMER);
        assertEquals("RESELLER type constant", 2, UserType.RESELLER);
    }

    @Test
    public void testMultipleLoginsWithTypeCheck() {
        // First login as customer
        AppSession.setLoggedInUser(testCustomer);
        assertTrue("Customer login successful", utility.ensureLoggedIn(UserType.CUSTOMER));

        // Logout
        AppSession.logout();

        // Login as reseller
        AppSession.setLoggedInUser(testReseller);
        assertTrue("Reseller login successful", utility.ensureLoggedIn(UserType.RESELLER));
    }

    @Test
    public void testSessionStateAfterLogout() {
        // Login customer
        AppSession.setLoggedInUser(testCustomer);
        assertTrue("Should be logged in", AppSession.isLoggedIn());

        // Logout
        AppSession.logout();
        
        // Check logged in status
        assertFalse("Should not be logged in after logout", utility.ensureLoggedIn(UserType.CUSTOMER));
    }

    @Test
    public void testEnsureLoggedInConsistency() {
        AppSession.setLoggedInUser(testCustomer);
        
        // Multiple calls should return consistent results
        boolean check1 = utility.ensureLoggedIn(UserType.CUSTOMER);
        boolean check2 = utility.ensureLoggedIn(UserType.CUSTOMER);
        
        assertEquals("Results should be consistent", check1, check2);
        assertTrue("Both checks should be true", check1 && check2);
    }

    @Test
    public void testEnsureLoggedInWithNullUser() {
        // Explicitly set null user
        AppSession.logout();
        boolean result = utility.ensureLoggedIn(UserType.CUSTOMER);
        
        assertFalse("Null user should not be logged in", result);
    }

    @Test
    public void testUserTypeValidation() {
        AppSession.setLoggedInUser(testCustomer);
        
        // Valid type checks
        boolean customerCheck = utility.ensureLoggedIn(UserType.CUSTOMER);
        assertTrue("Valid customer type check", customerCheck);
        
        // Invalid type checks
        boolean resellerCheck = utility.ensureLoggedIn(UserType.RESELLER);
        assertFalse("Invalid reseller type check", resellerCheck);
    }

    @Test
    public void testEnsureLoggedInBehaviorWithCustomerType() {
        User customer = new User();
        customer.setUserType(UserType.CUSTOMER);
        AppSession.setLoggedInUser(customer);

        boolean result = utility.ensureLoggedIn(UserType.CUSTOMER);
        assertTrue("Customer type should match", result);
    }

    @Test
    public void testEnsureLoggedInBehaviorWithResellerType() {
        User reseller = new User();
        reseller.setUserType(UserType.RESELLER);
        AppSession.setLoggedInUser(reseller);

        boolean result = utility.ensureLoggedIn(UserType.RESELLER);
        assertTrue("Reseller type should match", result);
    }

    @Test
    public void testSecurityCheck_CustomerAccess() {
        // Customer trying to access customer feature
        AppSession.setLoggedInUser(testCustomer);
        boolean hasAccess = utility.ensureLoggedIn(UserType.CUSTOMER);
        
        assertTrue("Customer should have access", hasAccess);
    }

    @Test
    public void testSecurityCheck_ResellerAccess() {
        // Reseller trying to access reseller feature
        AppSession.setLoggedInUser(testReseller);
        boolean hasAccess = utility.ensureLoggedIn(UserType.RESELLER);
        
        assertTrue("Reseller should have access", hasAccess);
    }

    @Test
    public void testSecurityCheck_UnauthorizedAccess() {
        // Customer trying to access reseller feature
        AppSession.setLoggedInUser(testCustomer);
        boolean hasAccess = utility.ensureLoggedIn(UserType.RESELLER);
        
        assertFalse("Customer should not access reseller feature", hasAccess);
    }

    @Test
    public void testSecurityCheck_UnauthorizedResellerAccess() {
        // Reseller trying to access customer feature
        AppSession.setLoggedInUser(testReseller);
        boolean hasAccess = utility.ensureLoggedIn(UserType.CUSTOMER);
        
        assertFalse("Reseller should not access customer feature", hasAccess);
    }

    @Test
    public void testLoginRequiredCheck() {
        // No one logged in
        assertFalse("Should require login for customer", utility.ensureLoggedIn(UserType.CUSTOMER));
        assertFalse("Should require login for reseller", utility.ensureLoggedIn(UserType.RESELLER));
    }

    @Test
    public void testEnsureLoggedInEdgeCases() {
        // Login customer
        AppSession.setLoggedInUser(testCustomer);
        
        // Check valid type
        assertTrue("Valid check should pass", utility.ensureLoggedIn(1)); // 1 = CUSTOMER
        
        // Check invalid type
        assertFalse("Invalid type should fail", utility.ensureLoggedIn(2)); // 2 = RESELLER
    }

    @Test
    public void testUtilityMethodAvailability() {
        // Verify utility method exists and is callable
        try {
            utility.ensureLoggedIn(UserType.CUSTOMER);
        } catch (Exception e) {
            fail("ensureLoggedIn method should be available: " + e.getMessage());
        }
    }

    @Test
    public void testRepeatedLoginLogoutCycles() {
        for (int i = 0; i < 5; i++) {
            // Login
            AppSession.setLoggedInUser(testCustomer);
            assertTrue("Cycle " + i + ": Customer should be logged in", 
                      utility.ensureLoggedIn(UserType.CUSTOMER));

            // Logout
            AppSession.logout();
            assertFalse("Cycle " + i + ": Customer should be logged out", 
                       utility.ensureLoggedIn(UserType.CUSTOMER));
        }
    }

    @Test
    public void testUserSwitchingWithTypeCheck() {
        // Start with customer
        AppSession.setLoggedInUser(testCustomer);
        assertTrue("Customer check", utility.ensureLoggedIn(UserType.CUSTOMER));
        
        // Switch to reseller
        AppSession.setLoggedInUser(testReseller);
        assertFalse("Old customer check should fail", utility.ensureLoggedIn(UserType.CUSTOMER));
        assertTrue("New reseller check should pass", utility.ensureLoggedIn(UserType.RESELLER));
    }
}
