package com.ecom.customer;

import com.ecom.user.FailedToCreateUserException;
import com.ecom.user.User;
import com.ecom.user.UserLogin;
import com.ecom.user.UserType;

public class CustomerLogin extends UserLogin {

    @Override
    public User registerUser() throws FailedToCreateUserException {
        System.out.println("Customer Registration");
        return super.registerUser(UserType.CUSTOMER);
    }

    @Override
    public User login() throws Exception {
        System.out.println("Customer Login");
        return super.login(UserType.CUSTOMER);
    }
}
