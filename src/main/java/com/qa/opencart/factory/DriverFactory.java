package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;



public class DriverFactory {
	
	public static String highlight;
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	
	public static  ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public WebDriver init_driver(Properties prop)  {
		
		String browerName = prop.getProperty("browser").trim();
		
		System.out.println("browser name is : " + browerName);
		
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		
		if(browerName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			//tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions())); // i am not removing this.you should
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//remote code
				init_remoteDriver("chrome");
			}
			
			else {
				//local
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
		}
		
		else if(browerName.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//remote code
				init_remoteDriver("firefox");
			}
			
			else {
				//local
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
		}
		
		else if(browerName.equals("safari")) {
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		}
		
		else {
			System.out.println("please specify the right browser " + browerName);
		}
		
		//driver.manage().window().fullscreen();
		//driver.manage().deleteAllCookies();
		//driver.get(prop.getProperty("url"));
		//return driver;
		getDriver().manage().window().fullscreen();
		getDriver().manage().deleteAllCookies();
		//getDriver().get(prop.getProperty("url"));--> let change this
		openURL(prop.getProperty("url"));
		return getDriver();
	}
	
	private void init_remoteDriver(String browser) {
		System.out.println("running test on remote grid server:" + browser);
		
		//Selenium 4.0
		if(browser.equalsIgnoreCase("chrome")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setBrowserName("chrome");
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(browser.equalsIgnoreCase("firefox")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setBrowserName("firefox");
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
		//Selnium 4.0 future. if they remove cap.setBrowserName
		if(browser.equalsIgnoreCase("chrome")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(browser.equalsIgnoreCase("firefox")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//Selenium 3 code
		/*if(browser.equalsIgnoreCase("chrome")) {
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());//in next video,naveen has removed this??wat:(
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(browser.equalsIgnoreCase("firefox")) {
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		
	}

	/**
	 * 
	 * getdriver(): it will return a thread local copy of the webdriver
	 * 
	 */
	
	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public Properties init_prop_first() {
		prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
	
	public Properties init_prop() {
		prop = new Properties();
		FileInputStream fs = null;

		String envName = System.getProperty("env");// qa/dev/stage/uat

		if (envName == null) {
			System.out.println("Running on PROD env: ");
			try {
				fs = new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Running on environment: " + envName);
			try {
				switch (envName.toLowerCase()) {
				case "qa":
					fs = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					fs = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					fs = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "uat":
					fs = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;

				default:
					System.out.println("please pass the right environment.....");
					break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			prop.load(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	
	/*
	 * take screenshot
	 * 
	 */

	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public void openURL(String url) {
		try {
		if(url==null) {
			throw new Exception("URL is null");
		}
		}
		catch(Exception e) {
			
		}
		getDriver().get(url);
	}
	
	public void openURL(URL url) {
		try {
			if(url==null) {
				throw new Exception("URL is null");
			}
			}
			catch(Exception e) {
				
			}
			getDriver().navigate().to(url);
	}
	
	//http://amazon.com/accpage/users.html
	//base is http://amazon.com and path is /accpage/users.html
	public void openURL(String baseUrl,String path) {
		try {
			if(baseUrl==null) {
				throw new Exception("baseUrl is null");
			}
			}
			catch(Exception e) {
				
			}
			getDriver().get(baseUrl+"/"+path);
	}
	
	
	////http://amazon.com/accpage/users.html?route=account/login
	//query param->route=account/login
	public void openURL(String baseUrl,String path,String queryParam) {
		try {
			if(baseUrl==null) {
				throw new Exception("baseUrl is null");
			}
			}
			catch(Exception e) {
				
			}
			getDriver().get(baseUrl+"/"+path+"?"+queryParam);
	}
	
	
	
}

















		
	
	
		



