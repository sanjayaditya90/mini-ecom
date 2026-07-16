package com.ecom.reseller;

import com.ecom.user.FailedToCreateUserException;
import com.ecom.user.User;
import com.ecom.user.UserLogin;
import com.ecom.user.UserType;

public class ResellerLogin extends UserLogin {

    @Override
    public User registerUser() throws FailedToCreateUserException {
        System.out.println("Reseller Registration");
        return super.registerUser(UserType.RESELLER);
    }

    @Override
    public User login() throws Exception {
        System.out.println("Reseller Login");
        return super.login(UserType.RESELLER);
    }
}
