package test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pojo.Browser;
import pom.LoginPage;
import utility.Report;
public class LoginPageTest extends BaseTest{
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
	public void loginWithBlankMobileNumber(){
		test=reports.createTest("loginWithBlankMobileNumber");
		LoginPage loginPage=new LoginPage(driver);
		loginPage.clickRegisterLink();
		loginPage.clickOnContinue();
	    String errorMsg=loginPage.getInvalidMobileNumberMsg();
	    Assert.assertTrue(errorMsg.contains("Please enter a valid mobile number"));
}
	@Test
	public void loginWithInvalidMobileNumber() throws InterruptedException {
		test=reports.createTest("loginWithInvalidMobileNumber");
		LoginPage loginPage=new LoginPage(driver);
		loginPage.clickRegisterLink();
		loginPage.invalidMobileNumber();
		loginPage.clickOnContinue();
	    String errorMsg=loginPage.getInvalidMobileNumberMsg();
	    Assert.assertTrue(errorMsg.contains("Please enter a valid mobile number"));
}	
	@Test
	public void loginWithBlankOTPFunctionality(){
		test=reports.createTest("loginWithBlankOTPFunctionality");
		LoginPage loginPage=new LoginPage(driver);
		loginPage.clickRegisterLink();
		loginPage.enterMobileNumber();
		loginPage.clickOnContinue();
		loginPage.clickOnSubmit();
	    String errorMsg=loginPage.submitBlankOTPMsg();
	    Assert.assertTrue(errorMsg.contains("Please enter valid One Time Password (OTP)."));
}
	@Test
	public void loginWithInvalidOTPFunctionality() throws InterruptedException {
		test=reports.createTest("loginWithInvalidOTPFunctionality");
		LoginPage loginPage=new LoginPage(driver);
		loginPage.clickRegisterLink();
		loginPage.enterMobileNumber();
		loginPage.clickOnContinue();
		//ENTER OTP
		Thread.sleep(15000);
		loginPage.clickOnSubmit();
	    String errorMsg=loginPage.getInvalidOTPMsg();
	    Assert.assertTrue(errorMsg.contains("Incorrect One Time Password (OTP)"));
}

	@Test
	public void verifyChangeMobileNo() throws InterruptedException {
		test=reports.createTest("verifyChangeMobileNo");
		LoginPage loginPage=new LoginPage(driver);
		loginPage.clickRegisterLink();
		loginPage.enterMobileNumber();
		loginPage.clickOnContinue();
		Thread.sleep(2000);
		loginPage.clickOnChangeNumber();
	    String changeMobile=loginPage.getHelpText();
	    System.out.println(changeMobile);
	    Assert.assertTrue(changeMobile.contains("Help :"));
}
	@Test
	public void registerAccountFunctionality() throws InterruptedException {
		test=reports.createTest("registerAccountFunctionality");
		LoginPage loginPage=new LoginPage(driver);
		loginPage.clickRegisterLink();
		loginPage.enterMobileNumber();
		loginPage.clickOnContinue();
		//ENTER OTP
		Thread.sleep(15000);
		loginPage.clickOnSubmit();
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement verifyHomePage=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='User' ]")));
		String home=loginPage.verifyHome();
		Assert.assertTrue(home.contains("User"));
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