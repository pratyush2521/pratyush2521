package stepdefinitions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.util.DebugUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.LoggerHelper;
import pageobjects.GooglemapsPage;
import utilities.GenericLib;

/**
 * @author Pratyush
 */
public class Steps {
	public static Logger logger = LoggerHelper.getLogger(Steps.class);
	GooglemapsPage googlemapsPage;

	public WebDriver driver;
	FileOutputStream outputStream;
	GenericLib genericLib;

	@Given("^I launch the \"([^\"]*)\" browser$")
	public void i_launch_the_browser(String browserName) {
		logger.info("Loading browser with ---> " + browserName);

		if (browserName.equalsIgnoreCase("firefox")) {
			logger.info("Launching Firefox Driver..!");
			System.setProperty("webdriver.gecko.driver", "./src/main/java/exefiles/geckodriver.exe");
			driver = new FirefoxDriver();
			logger.info("Firefox Launched");
			driver.manage().window().maximize();
		} else if (browserName.equalsIgnoreCase("chrome")) {
			logger.info("Launching Chrome Driver..!");
			System.setProperty("webdriver.chrome.driver", "./src/main/java/exefiles/chromedriver.exe");
			driver = new ChromeDriver();
			logger.info("Chrome Launched");
			driver.manage().window().maximize();
		} else if (browserName.equalsIgnoreCase("edge")) {
			logger.info("Launching Edge Driver..!");
			System.setProperty("webdriver.edge.driver", "./src/main/java/exefiles/msedgedriver.exe");
			driver = new EdgeDriver();
			logger.info("Edge Launched");
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Given("^I naviate to the URL \"([^\"]*)\"$")
	public void i_naviate_to_the_URL(String url) {
		driver.get(url);
		logger.info("URL-> " + url + " Navigated");
	}

	@When("^I search for \"([^\"]*)\" location$")
	public void i_search_for_location(String locationName) {
		googlemapsPage = new GooglemapsPage(driver);
		googlemapsPage.searchLocationinMaps(locationName);
	}

	@When("^I hit the \"([^\"]*)\" button$")
	public void i_hit_the_button(String buttonName) {
		googlemapsPage = new GooglemapsPage(driver);

		if (buttonName.equalsIgnoreCase("directions"))
			googlemapsPage.directionsBttn.click();
		else if (buttonName.equalsIgnoreCase("driving"))
			googlemapsPage.drivingBttn.click();
	}

	@Then("^I assert the coordinates of the location to be \"([^\"]*)\"$")
	public void i_assert_the_coordinates_of_the_location_to_be(String coordinates) throws InterruptedException {
		String[] latLong = coordinates.split(",");
		String expectedLat = latLong[0];
		String expectedLong = latLong[1];
		logger.info("Coordinates : " + expectedLat + "," + expectedLong);

		Thread.sleep(8000);
		String currentUrl = driver.getCurrentUrl();
		String[] urlLatlong = currentUrl.split("@");

		String latlong1 = urlLatlong[1];
		latlong1 = latlong1.substring(0, 23);
		urlLatlong = latlong1.split(",");
		String actualLat = urlLatlong[0];
		String actualLong = urlLatlong[1];

		if (expectedLat.equals(actualLat))
			logger.info("Latitude is matching");
		else {
			logger.info("Latitude NOT matching.." + "Expected-> " + expectedLat + " But Actual-> " + actualLat);
		}

		if (expectedLong.equals(actualLong))
			logger.info("Longitude is matching");
		else {
			logger.info("Longitude NOT matching.." + "Expected-> " + expectedLong + " But Actual-> " + actualLong);
		}
	}

	@Then("^I enter \"([^\"]*)\" to be the starting point$")
	public void i_enter_to_be_the_starting_point(String location) {
		googlemapsPage = new GooglemapsPage(driver);
		googlemapsPage.chooseStartingpoint(location);
	}

	@Then("^I verify the routes$")
	public int i_verify_the_routes() throws InterruptedException {
		int routes = 0;
		googlemapsPage = new GooglemapsPage(driver);

		routes = googlemapsPage.getNumberofRoutes().size();
		logger.info("Total available routes displayed in the list = " + routes);

		return routes;
	}

	@Then("^I print the various route details in \"([^\"]*)\"$")
	public void i_print_the_various_route_details_in(String fileName) throws InterruptedException, IOException {
		ArrayList<String> writer = new ArrayList<String>();
		int count=i_verify_the_routes();
		
		googlemapsPage = new GooglemapsPage(driver);
		genericLib = new GenericLib();
		
		for (int i = 0; i < count; i++) {
			String routeTitle,routeDistance,routeTime=null;

			WebElement elemTitle=driver.findElement(By.xpath(genericLib.getXpath("route_title").replace("INDEX", String.valueOf(i))));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elemTitle);
			
			routeTitle = elemTitle.getText();
			routeDistance = driver.findElement(By.xpath(genericLib.getXpath("route_distance").replace("INDEX", String.valueOf(i)))).getText();
			routeTime = driver.findElement(By.xpath(genericLib.getXpath("route_traveltime").replace("INDEX", String.valueOf(i)))).getText();

			writer.add("Route Title= via " + routeTitle + ", Distance= " + routeDistance + ", Travel Time= " + routeTime
					+ "\n");
		}
		logger.info(writer.toString());
		
		FileWriter filWriter=new FileWriter(new File(fileName));
		filWriter.write(writer.toString());
		filWriter.close();
	}
	
	@Then("^I close the browser$")
	public void i_close_the_browser() throws Throwable {
	    driver.quit();
	}
}
