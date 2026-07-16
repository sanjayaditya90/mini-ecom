package com.samsup.dashboard;

import com.samsup.user.User;

public class AppDashboard {

	public void dashboardPage() {
		
//		convert into menu
		AppLogin appLogin = new AppLogin();
		appLogin.loginPage();

		AppSession appSession = new AppSession();
		User user = appSession.getLoggedInUser();
		if (user.getUserType() == 2) {
			AppProduct appProduct = new AppProduct();
			appProduct.listProduct(user);
		}

	}
}
