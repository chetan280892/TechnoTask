package test;



import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pojo.Browser;
import pom.LoginPage;
import pom.SearchPage;
import utility.Report;
public class TechnodomeTask  extends BaseTest{
	ExtentReports reports;
	ExtentTest test;
	@BeforeTest
	public void setupReports() {
		reports=Report.createReports();
}
	@BeforeMethod
	public void launchApplication() {
		driver=Browser.launchBrowser();
	}
	
	@Test
	public void getItemTextAndPriceWithLogin() throws InterruptedException{
		test=reports.createTest("getItemTextAndPriceWithLogin");
		LoginPage loginPage=new LoginPage(driver);
		loginPage.clickRegisterLink();
		loginPage.enterMobileNumber();
		loginPage.clickOnContinue();
		//ENTER OTP
		Thread.sleep(20000);
		loginPage.clickOnSubmit();
		Thread.sleep(15000);
		SearchPage searchPage=new SearchPage(driver);
		searchPage.enterProduct("cooker");
		searchPage.clickSearchButton();
		String productTitle=searchPage.getProductDescription(2);
		System.out.println(productTitle);
		String productPrice=searchPage.getProductPrice(2);
		System.out.println(productPrice);
		Assert.assertTrue(productTitle.contains("5 + 3 Ltr Pressure"));
		Assert.assertTrue(productPrice.contains("1,999"));
}

	@Test
	public void getItemTextAndPriceWithoutLogin() throws InterruptedException{
		test=reports.createTest("getItemTextAndPriceWithoutLogin");
		SearchPage searchPage=new SearchPage(driver);
		searchPage.enterProduct("cooker");
		searchPage.clickSearchButton();
		String productTitle=searchPage.getProductDescription(1);
		System.out.println(productTitle);
		String productPrice=searchPage.getProductPrice(1);
		System.out.println(productPrice);
		Assert.assertTrue(productTitle.contains("7 Pcs Cookware"));
		Assert.assertTrue(productPrice.contains("1,499"));
}
	@AfterMethod
	public void getTestResult(ITestResult result) {
		if(result.getStatus()==ITestResult.SUCCESS) {
			
			test.log(Status.PASS,result.getName());
			
	}
	else if(result.getStatus()==ITestResult.FAILURE) {
		test.log(Status.FAIL,result.getName());
	}
	else if(result.getStatus()==ITestResult.SKIP) {
		test.log(Status.SKIP,result.getName());
	}
	}
	@AfterTest
	public void publishReports() {
	reports.flush();
	}
}
