package pageobjects;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helper.LoggerHelper;

public class GooglemapsPage {

	private static Logger logger = LoggerHelper.getLogger(GooglemapsPage.class);
	WebDriver driver;
	Actions actions;
	
	@FindBy(xpath = "//input[@aria-label='Search Google Maps']")
	private WebElement searchLocationTxtField;
	@FindBy(xpath = "//input[@aria-label='Search Google Maps']")
	private WebElement searchIcon;
	@FindBy(xpath="//button[contains(@aria-label,'Directions to')]")
	public WebElement directionsBttn;
	@FindBy(xpath="//img[@aria-label='Driving']")
	public WebElement drivingBttn;
	@FindBy(xpath="//input[contains(@aria-label,'Choose starting point')]")
	private WebElement startingPntTxtField;
	
	@FindAll(@FindBy(xpath="//div[contains(@id,'section-directions-trip')]"))
	private List<WebElement> numberofRoutes;

	public GooglemapsPage(WebDriver driver) {	
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	public void searchLocationinMaps(String locationName) {
		logger.info("Entering location name "+locationName+" in Maps !");
		actions=new Actions(driver);
		actions.moveToElement(searchLocationTxtField).sendKeys(locationName).sendKeys(Keys.ENTER).build().perform();;
	}
	
	public void chooseStartingpoint(String location) {
		Actions actions=new Actions(driver);
		logger.info("Choosing starting point in Map as : "+location);
		actions.moveToElement(startingPntTxtField).sendKeys(location).sendKeys(Keys.ENTER).build().perform();
	}

	public List<WebElement> getNumberofRoutes() {
		List<WebElement> elements=numberofRoutes;
		
		return elements;
	}
}
