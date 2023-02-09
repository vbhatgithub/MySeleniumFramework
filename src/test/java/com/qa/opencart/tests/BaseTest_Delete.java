package com.qa.opencart.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;

public class BaseTest_Delete {
	
	 DriverFactory df;
	 Properties prop;
	 WebDriver driver;
	 LoginPage loginpage;
	 AccountsPage accountPage;
	 SoftAssert softassert;
	 
	 
	 @BeforeClass
	 public void setup() {
		 df = new DriverFactory();
		prop =  df.init_prop();
		 driver = df.init_driver(prop);
		 loginpage = new LoginPage(driver);
		 softassert = new SoftAssert();
	 }
	 

	 
	 
	 @AfterClass
	 public void tearDown() {
		 driver.quit();
	 }
}
