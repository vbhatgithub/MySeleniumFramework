package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.utils.Constants;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void productInfoSetUp() {
		accountsPage = loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	

	@Test(priority=1)
	public void productHeaderTest() {
		searchResultPage = accountsPage.doSearch("MacBook");
		productInfoPage =  searchResultPage.selectProduct("MacBook Pro");
		Assert.assertEquals(productInfoPage.getProductHeader(),"MacBook Pro");
	}
	
	@Test(priority=2)
	public void productImagesTest() {
		searchResultPage = accountsPage.doSearch("iMac");
		productInfoPage =  searchResultPage.selectProduct("iMac");
		Assert.assertEquals(productInfoPage.getProductImages(),Constants.IMAC_IMAGES_COUNT);
		
	}
	
	@Test(priority=3)
	public void productInfoTest() {
		searchResultPage = accountsPage.doSearch("MacBook");
		productInfoPage =  searchResultPage.selectProduct("MacBook Pro");
		Map<String,String> actProductInfoMap = productInfoPage.getProductInfo();
		actProductInfoMap.forEach((k,v) -> System.out.println(k + ":" + v));
		softassert.assertEquals(actProductInfoMap.get("name"), "MacBook Pro");
		softassert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softassert.assertEquals(actProductInfoMap.get("price"), "$2,000.00");
		softassert.assertAll();
	}
}
