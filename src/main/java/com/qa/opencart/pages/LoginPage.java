package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;


public class LoginPage {

	public WebDriver driver;
	public ElementUtil eleutil;
	
	
	private By usernameField = By.name("email");
	private By passwordField = By.name("password");
	private By LoginButton = By.xpath("//input[@value='Login']");
	private By loginErrorMessage = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}
	
	
	@Step("getting login page title...")
	public String getLoginpageTitle() {
		
		return eleutil.getTitle();
	}
	
	public boolean getLoginPageURL() {
		return eleutil.waitForURLToContain(Constants.LOGIN_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
	}
	
	public AccountsPage doLogin(String userName,String password) {
		
		eleutil.doSendKeysByClearing(usernameField, userName);
		eleutil.doSendKeysByClearing(passwordField, password);
		eleutil.doClick(LoginButton);
		return new AccountsPage(driver);
	}
	
	
	public boolean doLoginWithWrongCredentials(String userName,String pwd) {
		System.out.println("try to login with wrong credentials: " + userName + ": " + pwd);
		eleutil.doSendKeys(usernameField, userName);
		eleutil.doSendKeys(passwordField, pwd);
		eleutil.doClick(LoginButton);
		String errorMessage = eleutil.doGetText(loginErrorMessage);
		System.out.println(errorMessage);
		if(errorMessage.contains(Constants.LOGIN_ERROR_MESSAGE)) {
			System.out.println("Login is not successful");
			return false;
		}
		return true;
	}
	
}
