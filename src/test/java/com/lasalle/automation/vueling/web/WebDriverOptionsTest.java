package com.lasalle.automation.vueling.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * - Window: get, getTitle, getCurrentUrl, getPageSource, close, quit
 * - Navigate: to, back, forward, refresh
 * - FindElement & FindElements
 * switchTo: frame, alert, window…
 * WebElement: click, clear, findElement/s, getAttribute, getText, sendkeys…
 * Locators según preferencia:
 * By Id
 * By name
 * By css: <a href="https://saucelabs.com/resources/articles/selenium-tips-css-selectors">selenium-tips-css-selector</a>
 * By xpath
 * Wait: implicitlyWait vs explicitWait (expected conditions)
 */
public class WebDriverOptionsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Test
    public void testWebDrives() {
        LOGGER.debug("start testWebDrive");

        // TODO download from https://www.selenium.dev/ecosystem/
        System.setProperty ("webdriver.chrome.driver","C:\\...\\chromedriver.exe" );
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        LOGGER.debug("driver started");

        // Esperas - Implícitas implicitlyWait => no such element: Unable to locate element
        // Se aplica a TODOS los elementos
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        driver.findElement(By.cssSelector("#checkbox-example > button")).click();
        WebElement implicitWaitElement = driver.findElement(By.id("message"));
        String implicitWaitElementText = implicitWaitElement.getText();
        Assertions.assertThat(implicitWaitElement.isDisplayed()).isTrue();
        LOGGER.debug("finish element, implicitWaitElementText:[{}]", implicitWaitElementText);

        // Esperas - Explícitas WebDriverWait => el elemento existe...
        // pero no es visible o no cumple alguna condición
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        driver.findElement(By.cssSelector("#start > button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        String text = element.getText();
        Assertions.assertThat(element.isDisplayed()).isTrue();
        LOGGER.debug("finish element, WebDriverWait, text:[{}]", text);

        // Esperas - Explícitas FluentWait
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        driver.findElement(By.cssSelector("#checkbox-example > button")).click();
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.of(60, ChronoUnit.SECONDS))
                .pollingEvery(Duration.of(2, ChronoUnit.SECONDS))
                .ignoring(Exception.class);
        WebElement fluentElement = fluentWait.until(webDriver -> webDriver.findElement(By.id("message")));
        String fluentElementText = fluentElement.getText();
        Assertions.assertThat(fluentElement.isDisplayed()).isTrue();
        LOGGER.debug("finish element, fluentElementText:[{}]", fluentElementText);

        driver.close();
        LOGGER.debug("driver closed");
    }

}
