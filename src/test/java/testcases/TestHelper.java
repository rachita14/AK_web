package testcases;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class TestHelper {
	
	/*
	 * Scrolls the page by given distance
	 */
	public static void scrolldown(int n, WebDriver driver) {
		
		// scroll down to click
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + n + ")", "");
	}
}
