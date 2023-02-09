package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By header = By.xpath("//a[text()='naveenopencart']");
	private By accountsSections = By.xpath("//div[@id='content']/h2");
	private By searchField = By.name("search");
	private By searchButton = By.xpath("//span[@class='input-group-btn']/button");
	private By logoutLink = By.linkText("Logout");
	
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccountPageTitle() {
		return eleUtil.doGetTitle(Constants.ACCOUNTS_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	
	public String getAccountsPageHeader() {
		return eleUtil.doGetText(header);
	}
	
	public boolean isLogoutLinkExists() {
		return eleUtil.doIsDisplayed(logoutLink);
	}
	
	public void logout() {
		if(isLogoutLinkExists()) {
			eleUtil.doClick(logoutLink);
		}
	}
	
	public List<String> getAccountSectionList() {
		List<WebElement> accSecList = eleUtil.waitForElementsToBeVisible(accountsSections, 10);
		List<String> accSecValList = new ArrayList<String>();
		for(WebElement e : accSecList) {
			String text = e.getText();
			accSecValList.add(text);
		}
		return accSecValList;
	}
	
	public boolean isSearchFieldExists() {
		return eleUtil.doIsDisplayed(searchField);
	}
	
	public SearchResultsPage doSearch(String productName) {
		System.out.println("searching the product " + productName);
		eleUtil.doSendKeys(searchField, productName);
		eleUtil.doClick(searchButton);
		//Thread.sleep(10000);
		return new SearchResultsPage(driver);
	}



}
