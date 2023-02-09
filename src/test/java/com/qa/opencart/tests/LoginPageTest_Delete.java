package com.qa.opencart.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest_Delete extends BaseTest{
	
	WebDriver driver;
	
	
	@Test
	public void testLoginpageTitle() {
		Assert.assertTrue(loginpagedelete.getLoginPageTitle());
	}

}
