package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.Errors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100 : Design Open cart App - Login Page")
@Story("US 101: OpenCart Login Design witj multiple features")
@Listeners(TestAllureListener.class)
public class LoginPageTest extends BaseTest{
	
	
	@Description("Login page title test")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=1)
	public void loginPageTitleTest() {
		String actTitle = loginpage.getLoginpageTitle();
		System.out.println("title is " + actTitle);
		Assert.assertEquals(actTitle,Constants.LOGIN_PAGE_TITLE,Errors.ACC_PAGE_HEADER_NOT_FOUND_ERROR_MESSG);
	}
	
	
	@Test(priority=2)
	public void loginPageURLTest() {
		
		Assert.assertTrue(loginpage.getLoginPageURL());
	}

	
	@Test(priority=3)
	public void loginTest() {
		accountsPage = loginpage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
		Assert.assertEquals(accountsPage.getAccountPageTitle(), Constants.ACCOUNTS_PAGE_TITLE);
	
	}
}
