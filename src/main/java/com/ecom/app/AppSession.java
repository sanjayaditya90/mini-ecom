package com.ecom.app;

import com.ecom.user.User;

public class AppSession {
	private static User loggedInUser;

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public static void setLoggedInUser(User user) {
		loggedInUser = user;
	}

	public static void logout() {
		loggedInUser = null;
	}

	public static boolean isLoggedIn() {
		return loggedInUser != null;
	}
}
