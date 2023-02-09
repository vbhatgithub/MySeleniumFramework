package com.qa.opencart.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil_delete {
	
	private WebDriver driver;
	private JavaScriptUtil jsutil;
	
	
	public ElementUtil_delete(WebDriver driver) {
		this.driver = driver;
		jsutil = new JavaScriptUtil(driver);
	}
	
	
	public static By getBy(String locator) {
		
		if(locator.startsWith("name=")) {
			return By.name(locator.substring(5));
		}
		
		else if(locator.startsWith("class=")) {
			return By.className(locator.substring(6));
		}
		
		else if(locator.startsWith("css=")) {
			return By.cssSelector(locator.substring(4));
		}
		
		
		else if(locator.startsWith("link=")) {
			return By.linkText(locator.substring(4));
			}
		
		else if(locator.startsWith("tag=")) {
			return By.tagName(locator.substring(4));
		}
		
		else if(locator.startsWith("id=")) {
			return By.tagName(locator.substring(4));
		}
		
		else if(locator.startsWith("xpath=")) {
			return By.tagName(locator.substring(4));
		}
		
		else if(locator.startsWith("//")) {
			return By.tagName(locator.substring(4));
		}
		
		else if(locator.startsWith(".//")) {
			return By.tagName(locator.substring(4));
		}
		else {
			return By.id(locator);
		}
		
	}
	
	public WebElement getElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(500));
		WebElement element;
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		catch(TimeoutException e) {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		return element;
	}
	
	public WebElement getElement(String locator) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(500));
		WebElement element;
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locator)));
		}
		
		catch(TimeoutException e) {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locator)));
		}
		return element;
	}
	
	public WebElement getElement(By locator,int timeout,long sleep) {
		return waitForElementToBeVisible(locator,timeout,sleep);
	}
	
	
	public WebElement waitForElementToBeVisible(By locator,int timeout,long sleep) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(500),Duration.ofMillis(sleep));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public WebElement findElement(String locator) {
		return driver.findElement(getBy(locator));
	}
	
	public WebElement findElement(By locator) {
		
		WebElement element = driver.findElement(locator);
		
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsutil.flash(element);
		}
		return element;
	}
	
	
	public void doClick(By locator) {
		findElement(locator).click();
	}
	
	
	public void clickDTElement(By locator) {
		waitForElementVisible(locator).click();
	}
	
	
	public WebElement waitForElementPrsent(By locator) {
		
		ExpectedCondition<WebElement> condition = new ExpectedCondition<WebElement>() {
			
			public WebElement apply(WebDriver driver) {
				
				WebElement toReturn = driver.findElement(locator);
				return toReturn == null ? null : toReturn;
			}
			
			
		};
		return waitForCondition(condition);
	}
	
public WebElement waitForElementVisible(By locator) {
		
		ExpectedCondition<WebElement> condition = new ExpectedCondition<WebElement>() {
			
			public WebElement apply(WebDriver driver) {
				
				WebElement toReturn = driver.findElement(locator);
				
				if(toReturn.isDisplayed() && toReturn!=null) {
					return toReturn;
				}
				return null;
			}
			
			
		};
		return waitForCondition(condition);
	}
	
	
	
	public <T> T waitForCondition(ExpectedCondition<T> condition) {
		
		return (T) waitForCondition(condition,500,500);
		
	}
	
	public <T> T waitForCondition(ExpectedCondition<T> condition,int timeout,long sleep) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofMillis(sleep));
		return wait.until(condition);
	}
	
	public void type(By locator,String value) {
		waitForElementVisible(locator).sendKeys(value);
	}
	
}
