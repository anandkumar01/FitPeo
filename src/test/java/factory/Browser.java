package factory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser {
	protected WebDriver driver;
	protected Properties property;

	@BeforeClass
	@Parameters({ "browser" })
	public WebDriver initializeBrowser(@Optional("Chrome") String browser) throws IOException {

		try {
			if (getProperties().getProperty("execution_env").equalsIgnoreCase("local")) {

				switch (browser.toLowerCase()) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
					break;
				case "edge":
					WebDriverManager.edgedriver().setup();
					driver = new EdgeDriver();
					break;
				default:
					System.out.println("No matching browser");
					driver = null;
				}
			}

			if (driver != null) {
				System.out.println("\nStarting the browser session..\n");
				driver.manage().deleteAllCookies();
				driver.get(property.getProperty("baseUrl"));
				driver.manage().window().maximize();
			}

		} catch (Exception e) {
			System.err.println("Error during browser initialization: " + e.getMessage());
		}
		return driver;
	}

//	@AfterClass
	public void closeBrowser() {
		if (driver != null) {
			System.out.println("\nClosing the browser session..");
			driver.quit();
		}
	}

	// creating a method to fetch the data from properties file
	public Properties getProperties() throws IOException {
		String propertyFile = System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties";
		try (FileReader file = new FileReader(propertyFile)) {
			property = new Properties();
			property.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return property;
	}
}
