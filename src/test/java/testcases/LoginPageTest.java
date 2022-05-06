package testcases;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPageTest {

	private static long noOfSeconds = 12; // wait duration in seconds
	Duration duration = Duration.ofSeconds(noOfSeconds); // Duration using ofSeconds() method
	public static WebDriver driver = null;

	private String browser;
	private String url;

	/*
	 * This method loads initial data viz. browser and url this is being populated
	 * from testng1.xml file
	 */
	@Parameters({ "browser", "url" })
	@BeforeTest
	public void preLoadInitData(String browser, String url) {
		this.browser = browser;
		this.url = url;
	}

	/*
	 * This method is used to setup driver based on browser also, used to launch the
	 * initial page for subsequent tests
	 */
	@BeforeClass
	public void setUp() {

		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}

		else if (browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(duration);

		System.out.println("Page Launched");
	}

	/*
	 * Tests SignIn {reads email and password from TestNG file}
	 */

	@Parameters({ "email", "password" })
	@Test(priority = 1, alwaysRun = true)
	public void signIn(String email, String password) throws InterruptedException {

		driver.findElement(By.linkText("Sign in")).click();
		driver.findElement(By.xpath("//input[@type=\"email\"]")).sendKeys(email);
		driver.findElement(By.xpath("//input[@type=\"password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();

		System.out.println("Sign In button clicked");
	}

	/*
	 * Tests Create Post {reads inputs from TestNG file}
	 */

	@Parameters({ "articletitle", "aboutarticle", "articledetails", "tags" })
	@Test(priority = 2)
	public void createPost(String articletitle, String aboutarticle, String articledetails, String tags)
			throws InterruptedException {

		articletitle = articletitle + "_" + System.currentTimeMillis();

		WebDriverWait wait = new WebDriverWait(driver, duration);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href=\"#editor\"]")));

		driver.findElement(By.xpath("//a[@href=\"#editor\"]")).click();
		driver.findElement(By.xpath("//input[@placeholder=\"Article Title\"]")).sendKeys(articletitle);
		driver.findElement(By.xpath("//input[@placeholder=\"What's this article about?\"]")).sendKeys(aboutarticle);
		driver.findElement(By.xpath("//textarea[@placeholder=\"Write your article (in markdown)\"]"))
				.sendKeys(articledetails);
		driver.findElement(By.xpath("//input[@placeholder=\"Enter tags\"]")).sendKeys(tags);
		driver.findElement(By.xpath("//button[@type=\"button\"]")).click();

		System.out.println("Post created successfully");
	}

	/*
	 * Tests Add comment {reads inputs from TestNG file}
	 */
	@Parameters({ "comment" })
	@Test(priority = 3)
	public void addComment(String comment) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, duration);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//textarea[@placeholder=\"Write a comment...\"]")));

		driver.findElement(By.xpath("//textarea[@placeholder=\"Write a comment...\"]")).sendKeys(comment);

		TestHelper.scrolldown(500, driver);

		wait = new WebDriverWait(driver, duration);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"btn btn-sm btn-primary\"]")))
				.click();

		driver.findElement(By.xpath("//button[@class=\"btn btn-sm btn-primary\"]")).click();

		System.out.println("Comment added successfully");
	}

	/*
	 * Tests Delete Post
	 */
	@Test(priority = 4)
	public void deletePost() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, duration);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@class=\"btn btn-outline-danger btn-sm\"]")));

		driver.findElement(By.xpath("//button[@class=\"btn btn-outline-danger btn-sm\"]")).click();

		System.out.println("Post deleted successfully");
	}

	/*
	 * Tests Logout
	 */
	@Test(priority = 5)
	public void logOut() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, duration);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href=\"#settings\"]")));

		driver.findElement(By.xpath("//a[@href=\"#settings\"]")).click();

		wait = new WebDriverWait(driver, duration);
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[@class=\"btn btn-outline-danger\"]")));
		
		TestHelper.scrolldown(500, driver);

		driver.findElement(By.xpath("//button[@class=\"btn btn-outline-danger\"]")).click();

		System.out.println("Navigate to settings and logout clicked successfully");
	}

	@AfterTest
	public void closeBrowser() {

		driver.close();
		System.out.println("Browser closed successfully");
	}

}
