package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import test.Baseclass;

public class LoginPage {

	
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	SoftAssert soft = new SoftAssert();	
	
	// ===== constructor ====
	public LoginPage () {
		
		// assign to class static obj
		driver = Baseclass.driver;
		report = Baseclass.report;
		test = Baseclass.test;
		
		PageFactory.initElements(driver, this);
		
	}

	
	// ==== WebElements : loginPage ===
	@FindBy(id="user-name")
	WebElement UserName;
	
	@FindBy(id="password")
	WebElement Password;
	
	@FindBy(name="login-button")
	WebElement Login;
	
	
	public void loginPage (String uname, String pass) {
		test = report.startTest("TC: Login SwagLabs");
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(UserName));
	
		UserName.sendKeys(uname);	
		test.log(LogStatus.PASS, "Successfully entered the user name");
		
		Password.sendKeys(pass);
		test.log(LogStatus.PASS, "Successfully entered the password");
		
		Login.click();	
		test.log(LogStatus.PASS, "Successfully clicked the login button");
		
		/*
		// Check if login error
		WebElement Error = driver.findElement(By.xpath("//button[@class='error-button']"));	
		String ExpMsg = "Epic sadface: Username and password do not match any user in this service";
		String ActMsg = Error.getText();
		
		soft.assertEquals(ActMsg, ExpMsg);
		*/
		
		/* -- hard way
		try {
			Assert.assertEquals(ExpMsg, ActMsg);
			test.log(LogStatus.PASS, "Expected and Actual value matches");
		} catch(Throwable e) {
			test.log(LogStatus.FAIL, "Expected and Actual value does not match");
		}
		
		*/
		
		
		/*
		// close after 5 seconds
		try {
			  Thread.sleep(5000);//time is in ms (1000 ms = 1 second)
			  driver.close();
		} catch (InterruptedException e) {e.printStackTrace();}			
		*/
	
	}

	
	

	
	
	
}
