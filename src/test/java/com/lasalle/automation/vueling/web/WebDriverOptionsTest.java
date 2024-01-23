package com.lasalle.automation.vueling.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;

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
        LOGGER.debug("driver started");
    }

    @AfterEach
    public void tearDown() {
        driver.close();
        LOGGER.debug("driver closed");
    }

    @Test
    public void testWebDrives() throws InterruptedException
    {
        driver.manage().window().maximize();
        LOGGER.debug("driver maximized");
        Assertions.assertThat(driver.getTitle()).isNotNull();
    }

    @Test
    public void testWebNavigation() throws InterruptedException
    {
        // TODO-1 navigate to https://the-internet.herokuapp.com
        // TODO-2 get title, url, pagesource
        // TODO-3 navigate to https://the-internet.herokuapp.com/abtest
        // TODO-4 navigate back, forward, refresh
    }
}
