package com.samsup.reseller;

import com.samsup.user.FailedToCreateUserException;
import com.samsup.user.User;
import com.samsup.user.UserLogin;
import com.samsup.user.UserType;

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
