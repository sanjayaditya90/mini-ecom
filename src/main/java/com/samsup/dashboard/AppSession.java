package com.samsup.dashboard;

import com.samsup.user.User;

public class AppSession {
	private User loggedInUser;
	
	public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public void logout() {
        loggedInUser = null;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
