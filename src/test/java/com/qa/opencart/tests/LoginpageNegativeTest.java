package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginpageNegativeTest extends BaseTest{
	
	@DataProvider
	public Object[][] loginWrongTestData(){
		return new Object[][] {
			{"test11@gmail.com","test1234"},
			{"test21@gmail.com","test1004"},
			{"   ","yydj"}
			
		
		};
	}
	
	@Test(dataProvider="loginWrongTestData")
	public void loginNegativeTest(String username,String password) {
		Assert.assertFalse(loginpage.doLoginWithWrongCredentials(username, password));
		
	}

}
