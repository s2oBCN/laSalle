package com.lasalle.automation.vueling.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * - Window: get, getTitle, getCurrentUrl, getPageSource, close, quit
 * - Navigate: to, back, forward, refresh
 * - FindElement & FindElements
 * switchTo: frame, alert, window…
 * WebElement: click, clear, findElement/s, getAttribute, getText, sendkeys…
 * Locators según preferencia:
 * By Id
 * By name
 * By css: https://saucelabs.com/resources/articles/selenium-tips-css-selectors
 * By xpath
 * Wait: implicitlyWait vs explicitWait (expected conditions)
 */
public class WebDriverOptionsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static WebDriver driver;

    @BeforeEach
    public void setUp() {
        LOGGER.debug("start testWebDrive");
        File currentDirFile = new File(".webDriver/chromedriver.exe");
        System.setProperty ("webdriver.chrome.driver",currentDirFile.getAbsolutePath() );
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        LOGGER.debug("driver started");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        LOGGER.debug("driver closed");
    }

    @Test
    public void testWebDriveNavigation() throws InterruptedException
    {
        LOGGER.debug("start testWebDriveNavigation");

        // Navigation
        driver.get("https://the-internet.herokuapp.com");
        driver.getTitle();
        driver.getCurrentUrl();
        driver.getPageSource();
        driver.navigate().to("https://the-internet.herokuapp.com/abtest");
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();
        Assertions.assertThat(driver.getCurrentUrl()).isEqualTo("https://the-internet.herokuapp.com/abtest");
    }

    @Test
    public void testSelectors(){
        driver.navigate().to("https://the-internet.herokuapp.com");
        // TODO find element by:
        //              id: page-footer
        //              linkText: "JavaScript Alerts" ==> AND  click it
        //              cssSelector: "#content > div > ul > li:nth-child(1) > button" ==> AND  click it
        // TODO close alert with switchTo alert
        // TODO find element by:
        //              xpath: "//*[@id=\"content\"]/div/ul/li[2]/button" AND  click it
        // TODO close alert with switchTo alert
        // TODO find elementS by:
        //              cssSelector: "button"
    }
}
