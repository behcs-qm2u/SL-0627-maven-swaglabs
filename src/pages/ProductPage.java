package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import test.Baseclass;

public class ProductPage {

		
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;

	WebDriverWait wait; 
	
	SoftAssert soft = new SoftAssert();	
	
	// ===== constructor ====
	public ProductPage () {
		
		driver = Baseclass.driver;
		report = Baseclass.report;
		test = Baseclass.test;
		
		wait = new WebDriverWait(driver,  10);
		
		PageFactory.initElements(driver, this);
		
	}


	// ==== Method : AddSpecificProduct() ===

	public void AddSpecificProduct(String productName) {
		
		test = report.startTest("TC: AddSpecificProduct");
		
		// -- Step 1: Ensure we're in the right page after login
		if (!verifyInProductPage()) {
			return;
		}
		
		// -- Step 2: Construct Dynamic XPath & Add the item
		
		// construct dynamic xpath
		String dynXpath = new String();

		/* method 1
			// Example: //button[@data-test="add-to-cart-sauce-labs-bike-light"]
			productName.toLowerCase();
			dynXpath="//button[@data-test=\"add-to-cart-" + productName.toLowerCase().replace(" ", "-") +"\"]";
		*/

		/* method 2 */
		dynXpath="//*[text()='"+ productName +"']//following::button[1]";

		
		// Check if product listed in the page? If yes, click ADD TO CART button
	    try {
	    	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dynXpath)));
	    	driver.findElement(By.xpath(dynXpath)).click();
			test.log(LogStatus.INFO, "Product [" + productName + "] found, clicked ADD TO CART button.");	
	    } catch (TimeoutException e) {
			test.log(LogStatus.FAIL, "Product [" + productName + "] not found");
			return;
	    }
		

		// -- Step 3: Verify item added via number(s) of item in Cart Badge
	    
		// Perform simple verification using the Cart Badge on Top Right of the page
		// Now we update the dynXpath to remove pattern
		dynXpath="//button[@data-test=\"remove-" + productName.toLowerCase().replace(" ", "-") +"\"]";
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dynXpath)));
	    try {
	    	driver.findElement(By.xpath(dynXpath));
			test.log(LogStatus.PASS, "Successful added [" + productName + "] to cart");	
	    } catch (TimeoutException e) {
			test.log(LogStatus.FAIL, "Fail to add [" + productName + "] to cart");
			soft.fail("Failed to add item to cart!");	
	    }
		
		
	}
	

	
	// ===== WebElements : AddNProduct =====

	@FindBy(xpath="//button[@class='btn btn_primary btn_small btn_inventory']")
	List<WebElement> ProductList;
    // List<WebElement> CartList = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory']"));
	
	// ===== Method : AddNProduct =====	
	
	public void AddNProduct(int num) {

		test = report.startTest("TC: AddNProduct");
		
		test.log(LogStatus.INFO, "Going to add ("+num+") item from product listing to cart");
		
		// -- Check 1: Ensure we're in the right page after login
		if (!verifyInProductPage()) {
			return;
		}

	    // -- Check 2: Ensure there are at least 2 products listed. Then, proceed to add them

		int totalProductListed = ProductList.size();
		/* Note:
		 *  once we clicked, the button become btn_secondary. Hence, we need to save the ProductList first.
		 *  "//button[@class='btn btn_primary btn_small btn_inventory']")	
		 */

		if ( ProductList.size() >= num) {
			
			for ( int i = 0; i < num; i++) {
				ProductList.get(i).click();
			}
			
			// -- Check 2a: Additional check on the Cart Badge 
			
			// confirm 2 items displayed on the cart badge, noticed the span won't appear if no item added
			// so, we wait until element valid
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='shopping_cart_badge']")));
			WebElement cartBadge = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
			
			if ( cartBadge.getText().contentEquals(String.valueOf(num)) ) {
				test.log(LogStatus.INFO, "There is/are (" + totalProductListed + ") product(s) listed");
				test.log(LogStatus.PASS, "Successful added (" + num + ") item(s) to cart");	
			}
			else {
				test.log(LogStatus.FAIL, "Not able to add (" + num + ") item(s) to cart, cart badge verification failed");
			}
			
		} 
		else {
			test.log(LogStatus.INFO, "There are only ("+ ProductList.size() + ") product listed");
			test.log(LogStatus.FAIL, "There are less than (" + num + ") product(s) in the page. Abort adding to cart.");	
		}
		
	}
	

	
	@FindBy(xpath="//span[@class='title']")
	WebElement Title;
 
	public boolean verifyInProductPage() {
		
		// -- Check 1: Ensure we're in the right page after login
		String ExpMsg = "PRODUCTS";	// confirmation msg
		String ActMsg = Title.getText();
	    try {
	    	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='inventory_container']")));
			Assert.assertEquals(ActMsg, ExpMsg);
			test.log(LogStatus.PASS, "Successful assert we have landed on PRODUCTS page");	
	    }
	    catch (TimeoutException e) {
			test.log(LogStatus.FAIL, "Error: Login failed? Not leading to PRODUCTS page?");
			Assert.fail("Login failed? Not leading to PRODUCTS page?");
			return false;
	    }
		catch (Throwable e) {
			test.log(LogStatus.FAIL, "Error: Not on PRODUCTS page?!");
			Assert.fail("Not in PRODUCTS page");
			return false; 
		}
		return true;
		
	}
	
	
}
