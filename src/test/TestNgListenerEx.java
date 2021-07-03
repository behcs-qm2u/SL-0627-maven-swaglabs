package test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class TestNgListenerEx implements ITestListener {

	
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
		System.out.println("VERBOSE: Test started, into listener class");
		
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub 
		
		
		
		/* Note: usually test success no need to capture screen
		 *  - maybe we add this only if test on grid
		 */
		if (Baseclass.gridFlag) {
			RemoteWebDriver Rdriver = Baseclass.Rdriver;
			File myFile = Rdriver.getScreenshotAs(OutputType.FILE);

			try {
				FileUtils.copyFile(myFile, new File("test-output/testpass.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub 
	
		WebDriver driver = Baseclass.driver;
		
		
		
		TakesScreenshot TsObj = (TakesScreenshot) driver;
		
		File myFile = TsObj.getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(myFile, new File("test-output/testfailed.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub 
		
		System.out.println("VERBOSE: Test Skipped, into listener class");
		
	}
	

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub 
		
		
	}
	
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub 
		
		System.out.println("VERBOSE: onStart, into listener class");
	}
	
	@Override
	public void onFinish(ITestContext result) {
		// TODO Auto-generated method stub 
		
		System.out.println("VERBOSE: onFinish, into listener class");
	}
	
	
	
}
