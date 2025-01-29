package com.lasalle.automation.vueling.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import java.time.Duration;

/**
 * - Window: get, getTitle, getCurrentUrl, getPageSource, close, quit
 * - Navigate: to, back, forward, refresh
 * - FindElement & FindElements
 * switchTo: frame, alert, window…
 * WebElement: click, clear, findElement/s, getAttribute, getText, sendkeys…
 * Locators según preferencia:
 * By Id
 * By name
 * By css: <a href="https://saucelabs.com/resources/articles/selenium-tips-css-selectors">...</a>
 * By xpath
 * Wait: implicitlyWait vs explicitWait (expected conditions)
 */
public class WebDriverOptionsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static WebDriver driver;

    @BeforeEach
    public void setUp() {
        LOGGER.debug("start testWebDrive");
        // TODO download from https://www.selenium.dev/ecosystem/
        File currentDirFile = new File(".webDriver/chromedriver");
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
    public void testWebDriveImplicitlyWait()
    {
        LOGGER.debug("start testWebDriveImplicitlyWait");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        driver.findElement(By.cssSelector("#checkbox-example > button")).click();
        WebElement implicitWaitElement = driver.findElement(By.id("message"));
        String implicitWaitElementText = implicitWaitElement.getText();
        Assertions.assertTrue(implicitWaitElement.isDisplayed());
        LOGGER.debug("finish element, implicitWaitElementText:[{}]", implicitWaitElementText);
    }

    @Test
    public void testWaitCondition(){
        // Esperas - Explícitas WebDriverWait => el elemento existe...
        // pero no es visible o no cumple alguna condición
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        driver.findElement(By.cssSelector("#start > button")).click();
        // TODO create WebDriverWait
        //  ExpectedConditions.visibilityOfElementLocated(By.id("finish"))
    }

}
