package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

public class CartPage {

	
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	SoftAssert soft = new SoftAssert();	
	
	// ===== constructor ====
	public CartPage () {
		
		// assign to class static obj
		driver = Baseclass.driver;
		report = Baseclass.report;
		test = Baseclass.test;
		
		PageFactory.initElements(driver, this);
		
	}


	// ==== WebElements : AddSpecificProduct ===
	
	@FindBy(xpath="//div[@class='inventory_item_name']")
	List<WebElement> ItemList;

	//div[@class="inventory_item_name"]//following::div[1]
	//div[@class="inventory_item_desc"]
	@FindBy(xpath="//div[@class='inventory_item_name']//following::div[1]")
	List<WebElement> DescList;
	
	// //button[@data-test="add-to-cart-sauce-labs-bike-light"]
	
	
	public void VerifySpecificProduct(String productName) {
		
		test = report.startTest("TC: VerifySpecificProduct");
		
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		String sCartIconXpath = "//a[@class='shopping_cart_link']";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(sCartIconXpath)));
		driver.findElement(By.xpath(sCartIconXpath)).click();

		test.log(LogStatus.PASS, "We have entered the Cart Page");
		test.log(LogStatus.INFO, "Going to verify product [" + productName + "]");
		
		int totalCartListed = ItemList.size();
		/* Note:
		 *  once we clicked, the button become btn_secondary. Hence, we need to save the ProductList first.
		 *  "//button[@class='btn btn_primary btn_small btn_inventory']")	
		 */

		if ( totalCartListed > 0 ) {
			
			for ( int i = 0; i < totalCartListed; i++) {
				if ( ItemList.get(i).getText().equals(productName) ) {
					test.log(LogStatus.PASS, "Product [" + productName + "] found in cart!");	
					
					test.log(LogStatus.INFO,"Product Desc : "+ DescList.get(i).getText());
					
				}
				else {
					test.log(LogStatus.FAIL, "Product [" + productName + "] NOT found in cart!");
				}
			}
		} 
		else {
			
			test.log(LogStatus.FAIL, "Empty cart!");
		}
	}
	
	
	
}
