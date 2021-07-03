package test;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GridExample {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		// TODO Auto-generated method stub
		
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setPlatform(Platform.LINUX);
		cap.setBrowserName("chrome");
		
		String sURL = "http://192.168.100.41:4444/wd/hub";
		
		
		WebDriver driver = new RemoteWebDriver(new URL(sURL), cap);

		driver.get("https://www.saucedemo.com");
		
		
		Thread.sleep(5000);
		driver.quit();
		
	}

}
