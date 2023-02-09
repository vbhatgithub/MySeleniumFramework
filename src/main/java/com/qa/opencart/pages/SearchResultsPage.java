package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By productResults = By.xpath("//div[@class='caption']//a");
	//private By productResults = By.cssSelector("div.caption a");
	
	public int getProductsListCount() {
		int resultCount = eleUtil.waitForElementsToBeVisible(productResults, 10,2000).size();
		System.out.println("The search product count: " + resultCount);
		return resultCount;
	}

	public ProductInfoPage selectProduct(String mainProductName) {
		System.out.println("main product name is : " + mainProductName);
		List<WebElement> searchList = eleUtil.waitForElementsToBeVisible(productResults,10,2000);
		for(WebElement e : searchList) {
			String text = e.getText();
			if(text.equals(mainProductName)) {
				e.click();
				break;
			}
		}
		return new ProductInfoPage(driver);
	}
}
