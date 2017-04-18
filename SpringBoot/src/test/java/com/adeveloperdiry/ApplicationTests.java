package com.adeveloperdiry;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;

@SpringBootTest
public class ApplicationTests {

	@Test
	public void verifySearch() {
		File file = new File("C:/Users/uddasari/Downloads/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		//WebDriver driver = new ChromeDriver();
		WebDriver driver = createDriver("chrome" , "51.0", "Windows 10");
		driver.get("http://stackoverflow.com/questions/18473130/shopping-cart-java-application-addtocart");
		driver.quit();
	}

	private static final String USERNAME = "uddasari";
	private static final String ACCESS_KEY = "dffc0c11-ffbb-4958-8726-80c28f4d8551";
	
	public static WebDriver createDriver(String browser, String version, String os) {
	    // Should probably validate the arguments here
		try {
		    DesiredCapabilities capabilities = new DesiredCapabilities();
		    capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
		    capabilities.setCapability(CapabilityType.VERSION, version);
		    capabilities.setCapability(CapabilityType.PLATFORM, os);
		    return new RemoteWebDriver(new URL("http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
		    throw new RuntimeException("Failure forming the URL to create a web driver", e);
		}
	}
}
