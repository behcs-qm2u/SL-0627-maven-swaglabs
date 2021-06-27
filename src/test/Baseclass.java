package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Baseclass {

	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest test;
	
	SoftAssert soft = new SoftAssert();
	
	public static XSSFWorkbook wbook;
	public static XSSFSheet sheet;
	
	
	@BeforeTest
	public void setup() throws IOException {
		
		System.setProperty("webdriver.chrome.driver", "chromedriver");

		driver = new ChromeDriver();

		driver.get("https://www.saucedemo.com");

		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);

		report = new ExtentReports("ExtentReportSD.html");
		
		FileInputStream fis = new FileInputStream("data/exceldatasd.xlsx");
		
		wbook = new XSSFWorkbook(fis);
		sheet = wbook.getSheetAt(0);
	
			
	}
	
	
/*
  @AfterMethod(enabled=false)
   public void getResult(ITestResult result)
   {
       if(result.getStatus()==ITestResult.FAILURE)
       {
           test.log(LogStatus.FAIL, result.getThrowable());

       }
   }	
*/
	
	@AfterTest
	public void teardown() {
		System.out.println("in Teardown");

		report.endTest(test);
		report.flush();

		try {
			  Thread.sleep(3000); // wait 5 seconds
			  driver.quit();
		} catch (InterruptedException e) {e.printStackTrace();}			

	
	}

	
}
