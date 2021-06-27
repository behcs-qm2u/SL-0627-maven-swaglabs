package test;


/*
 * - https://www.saucedemo.com
 * - login
 * - add item to card : but use @Method depends on
 * 
 */

import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;

public class ProductSelectionTest extends Baseclass {



	// TestCase 1A: using dependsOnGroups to call the login page, add N item
	
	@Test(enabled=false, dependsOnGroups="loginGrp", description="add N item to cart")
	@Parameters({"numberOfProduct"})
	public void testcase1a(int num) {

		ProductPage productObj = new ProductPage();
		productObj.AddNProduct(num);
		
	}

	// TestCase 1B: using dependsOnGroups to call the login page, add Specific item
	
	@Test(enabled=true, groups= {"productGrp"}, dependsOnGroups="loginGrp", description="Add specific item to cart")
	public void testcase1b() {
		
		ProductPage productObj = new ProductPage();
		String product = sheet.getRow(1).getCell(2).getStringCellValue();
		productObj.AddSpecificProduct(product);
		
	}

	// TestCase 2: using dependsOnGroups to call the login page, add Specific item, verify they're in the cart
	
	@Test(enabled=true, dependsOnGroups="productGrp", description="Verify the specific item added to cart")
	public void testcase2() {
		
		CartPage cartObj = new CartPage();
		String product = sheet.getRow(1).getCell(2).getStringCellValue();
		cartObj.VerifySpecificProduct(product);
		
	}
	
	
	
}

