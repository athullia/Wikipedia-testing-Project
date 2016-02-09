package com.wiki.wiki_webui_test;


import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class WikipediaTest {
		
	static WebDriver driver;
	static String response ;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 driver = new FirefoxDriver();
		 driver.manage().window().maximize();

	}
	@Test
	public void wikipediaSignIn(){
		//Go to Wikipedia website https://www.wikipedia.org/
		driver.get("https://www.wikipedia.org/");
		
		//Verify wikipedia homepage is displayed
		//Validate the Text "The Free Encyclopedia" is in the HTML response
	    response = driver.getPageSource();
		assertTrue("Validation Failed - Text \"Wikipedia page\"Not Found",response.contains("The Free Encyclopedia"));
		
		//Verify Selecting language English is working
		//*[@id='www-wikipedia-org']/div[1]/div[2]/a/strong
		WebElement languageSelect = driver.findElement(By.xpath("//strong[text()='English']"));
		languageSelect.click();
		
		//Open Log in page
		WebElement logIn = driver.findElement(By.xpath("//a[text()='Log in']"));
		logIn.click();
		
		//Verify Log in page is displayed
		response = driver.getPageSource();
		assertTrue("Validation Failed - Text \"Username field\"Not Found",response.contains("Enter your username"));
		
		//Verify Log in page
		WebElement username = driver.findElement(By.id("wpName1"));
		//Clearing the box to verify there is no text there
		username.clear();
		username.sendKeys("Your User Name");
		
		//Waits until it finds the element password field
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("wpPassword1")));
		
		//Log in by entering the password
		WebElement passwordText = driver.findElement(By.id("wpPassword1"));
		passwordText.clear();
		
		passwordText.sendKeys("Your Password");
		WebElement login = driver.findElement(By.id("wpLoginAttempt"));
		login.click();
		
		//Verifying if the page contains the text "From today's featured article"
		response = driver.getPageSource();
		assertTrue("Validation Failed - Text \"Welcome to Wikipedia\"Not Found",response.contains("From today's featured article"));
		
		//Verifying whether the search field works
		WebElement search = driver.findElement(By.id("searchInput"));
		search.clear();
		search.sendKeys("usa");
		
		WebElement buttonSearch = driver.findElement(By.id("searchButton"));
		buttonSearch.click();

		//Verifying whether the search field works correctly
		response = driver.getPageSource();
		assertTrue("Validation Failed - Text \"United States\"Not Found",response.contains("United States"));
		
		//verify whether the wikipedia logo leads to home page
		WebElement mainPage = driver.findElement(By.id("p-logo"));
		mainPage.click();
		
	    wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pt-logout")));
		
		//Finds the log out element
		WebElement signOut = driver.findElement(By.id("pt-logout"));
		
		//Verify whether the user is logged out
		signOut.click();
		response = driver.getPageSource();
		assertTrue("Validation Failed - Text \"You are now logged out.\"Not Found",response.contains("You are now logged out."));
		
		
		//verify whether the wikipedia logo leads to home page
				WebElement mainPageTwo = driver.findElement(By.id("p-logo"));
				mainPageTwo.click();

		}
	
	
	@After 
	public void tearDown(){
		driver.manage().timeouts().implicitlyWait(100000, TimeUnit.SECONDS);

		driver.close();
		
	}
	
}
