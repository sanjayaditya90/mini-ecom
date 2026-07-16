package com.samsup.Util;

import com.samsup.app.AppSession;
import com.samsup.dashboard.LoginDashboard;

public class Utility {
	AppSession appSession = new AppSession();

	public boolean ensureLoggedIn(int userType) {

		if (appSession.getLoggedInUser() != null && userType == appSession.getLoggedInUser().getUserType()) {
			return true;
		}

		System.out.println("You must login first.");
		System.out.println("Redirecting to Login...");

		LoginDashboard loginDashboard = new LoginDashboard();
		loginDashboard.loginPage();

		return appSession.getLoggedInUser() != null;
	}
}
