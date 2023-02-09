package com.qa.opencart.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	public static By getBy(String locator)
	   {
	      if (locator.startsWith("name="))
	      {
	         return By.name(locator.substring(5));
	        		 
	      }
	      else if (locator.startsWith("css="))
	      {
	         return By.cssSelector(locator.substring(4));
	      }
	      else if (locator.startsWith("class="))
	      {
	         return By.className(locator.substring(6));
	      }
	      else if (locator.startsWith("link="))
	      {
	         return By.linkText(locator.substring(5));
	      }
	      else if (locator.startsWith("tag="))
	      {
	         return By.tagName(locator.substring(4));
	      }
	      else if (locator.startsWith("xpath="))
	      {
	         return By.xpath(locator.substring(6));
	      }
	      else if (locator.startsWith("//"))
	      {
	         return By.xpath(locator);
	      }
	      else if (locator.startsWith(".//"))
	      {
	         return By.xpath(locator);
	      }
	      else if (locator.startsWith("id="))
	      {
	         return By.id(locator.substring(3));
	      }
	      else
	      {
	         return By.id(locator);
	      }
	   }
	
	/*public By getBy(String locatorType, String locatorValue) {
		By locator = null;

		switch (locatorType.toLowerCase()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "classname":
			locator = By.className(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "cssselector":
			locator = By.cssSelector(locatorValue);
			break;
		case "linktext":
			locator = By.linkText(locatorValue);
			break;

		default:
			System.out.println("please pass the right locator type and value.....");
			break;
		}

		return locator;

	}
	*/
	
	public WebElement getElement(By locator,int timeout,int sleep) {
		return waitForElementVisible(locator,timeout,sleep);
	}
	
	public WebElement getElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(500));
		WebElement element = null;
		try
	      {
	         element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	      }
	      catch (TimeoutException te)
	      {
	         //do more one time
	         return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	      }
	      return element;
	}
	
	public WebElement findElement(By locator) {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}
	
	public void doClear(By locator) {
		findElement(locator).clear();
	}

	public void doSendKeysByClearing(By locator, String value) {
		doClear(locator);
		findElement(locator).sendKeys(value);
	}
	
	public WebElement waitForElementVisible(By locator,int timeout,long sleep) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout), Duration.ofMillis(sleep));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	
	public void doSendKeys(By locator,String value) {
		doClear(locator);
		waitForElementVisible(locator).sendKeys(value);
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
	 public  String getCurrentURL()
	  {
		  return driver.getCurrentUrl();
	  }
	 
	 public void doClick(By locator) {
			findElement(locator).click();
		}
	 
	 public void clickDTElement(By locator) {
		 waitForElementPresent(locator).click();
		 
		 
	 }
	 
	 public WebElement waitForElementPresent(final By locator) {
		 
		 ExpectedCondition<WebElement> condition = new ExpectedCondition<WebElement>() {
			 			 
			 public WebElement apply(WebDriver driver) {
				WebElement toReturn =  driver.findElement(locator);
				return toReturn == null ? null : toReturn;
			 }
			 
		 };
		 
		 return waitForCondition(condition);
	 }
	 
	 public  WebElement getElement(String locator)
	   {

		 WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(500));
	      WebElement element = null;
	      try
	      {
	         element = wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locator)));
	      }
	      catch (TimeoutException te)
	      {
	         //do more one time
	         return wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(locator)));
	      }
	      return element;
	   }
	 
	 public WebElement findElement(String locator){
	    	
	        return driver.findElement(getBy(locator));
	     
	    }
	
	public String doGetTitle(String title, int timeOut) {
		if (waitForTitleToBe(title, timeOut)) {
			return driver.getTitle();
		}
		return null;
	}
	
	public String doGetText(By locator) {
		return getElement(locator).getText();
	}
	
	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public boolean waitForTitleToBe(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.titleIs(title));
	}
	
	public boolean waitForURLToContain(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.urlContains(urlFraction));
	}
	
	public void type(String locator, String value){
    	waitForElementVisible(locator).sendKeys(value);
    }
	
	public WebElement waitForElementVisible(final By locator) {
		
		ExpectedCondition<WebElement> condition = new ExpectedCondition<WebElement>() {
			
			public WebElement apply(WebDriver driver) {
				WebElement toReturn = driver.findElement(locator);
				if(toReturn!=null && toReturn.isDisplayed()) {
					return toReturn;
				}
				return null;
			}
			
		};
		return waitForCondition(condition);
	}
	
	public WebElement waitForElementVisible(final String locator) {
    	final By by = getBy(locator);
        ExpectedCondition<WebElement> condition = new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement toReturn = driver.findElement(by);
                if (toReturn!=null && toReturn.isDisplayed()) {
                    return toReturn;
                }
                return null;
            }
        };
        return waitForCondition(condition);
        
    }
	
	
	public <T> T waitForCondition(ExpectedCondition<T> condition) {
		//return (T) waitForCondition(condition,timeout,DEFAULT_SLEEPER);
		return (T) waitForCondition(condition,500,500);
		
	}
	
	public <T> T waitForCondition(ExpectedCondition<T> condition, int timeout, long sleep) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofMillis(sleep));
		return wait.until(condition);
		
	}

/*	public By getBy(String locator) {
		
		if(locator.startsWith("xpath")) {
			return By.xpath(locator);
		}
		
		else if(locator.startsWith("id")) {
			
		}
		
		else if(locator.startsWith(""))
	}*/
	
	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	
	public void check(String locator) {
    	WebElement element = findElement(locator);
        if(!element.isSelected()){
            element.click();
        }
    }
    
    public void uncheck(String locator) {
    	WebElement element = findElement(locator);
        if(element.isSelected()){
            element.click();
        }
    }
	
}
