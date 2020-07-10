package smp.ytr.util.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumUtil {

	/*
	public static final String ELEMENTS_TPYE_ID = "ID";
	public static final String ELEMENTS_TPYE_CLASS = "CLASS";
	public static final String ELEMENTS_TPYE_TAG = "TAG";
	public static final String FIREFOX_DRIVER_PATH = "/Users/smpark/Desktop/geckodriver";
	
	public static final String YOUTUBE_CANNEL_SCH_URL = "https://www.youtube.com/results?sp=EgIQAg%253D%253D&search_query=";
	
	private WebDriver driver = null;
	
	public WebDriver getWebDriver() {
		return driver;
	}

	private void setWebDriver(WebDriver driver) {
		this.driver = driver;
	}

	public SeleniumUtil() {
		
		System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		driver = new FirefoxDriver(options);
		
		setWebDriver(driver);
	}

	
	//
	public void wait(String elementType, String waitElement, int waitSecond) {
		elementType = elementType.toUpperCase();
		
		WebDriverWait wait = new WebDriverWait(driver, waitSecond);
		if(ELEMENTS_TPYE_ID.equals(elementType)) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(waitElement)));
		}else if(ELEMENTS_TPYE_CLASS.equals(elementType)) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(waitElement)));
		}else if(ELEMENTS_TPYE_TAG.equals(elementType)) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(waitElement)));
		}
	}
	*/
	
}
