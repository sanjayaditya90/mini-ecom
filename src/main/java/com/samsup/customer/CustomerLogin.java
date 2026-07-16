package com.samsup.customer;

import com.samsup.user.FailedToCreateUserException;
import com.samsup.user.User;
import com.samsup.user.UserLogin;
import com.samsup.user.UserType;

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
