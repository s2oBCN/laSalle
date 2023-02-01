package com.lasalle.automation.vueling.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
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
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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

    @Test
    public void testWebDrives() throws InterruptedException
    {
        LOGGER.debug("start testWebDrive");

        System.setProperty ("webdriver.chrome.driver","/home/s2o/tmp/chromedriver_linux64/chromedriver" );
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        LOGGER.debug("driver started");

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
        LOGGER.debug("navigation ok");

        // Selectors
        driver.navigate().to("https://the-internet.herokuapp.com");
        driver.findElement(By.id("page-footer"));
        driver.findElement(By.linkText("JavaScript Alerts")).click();
        driver.findElement(By.cssSelector("#content > div > ul > li:nth-child(1) > button")).click();
        driver.switchTo().alert().accept();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[2]/button")).click();
        driver.switchTo().alert().dismiss();
        List<WebElement> buttons = driver.findElements(By.cssSelector("button"));
        Assertions.assertThat(buttons.size()).isEqualTo(3);
        LOGGER.debug("selectors ok");

        // Esperas - Explícitas FluentWait
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        driver.findElement(By.cssSelector("#checkbox-example > button")).click();
        Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.of(60, ChronoUnit.SECONDS))
                .pollingEvery(Duration.of(2, ChronoUnit.SECONDS))
                .ignoring(Exception.class);
        WebElement fluentElement = fluentWait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.id("message"));
            }
        });
        String fluentElementText = fluentElement.getText();
        Assertions.assertThat(fluentElement.isDisplayed()).isTrue();
        LOGGER.debug("finish element, fluentElementText:[{}]", fluentElementText);

        // Esperas - Implícitas implicitlyWait
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        driver.findElement(By.cssSelector("#checkbox-example > button")).click();
        WebElement implicitWaitElement = driver.findElement(By.id("message"));
        String implicitWaitElementText = implicitWaitElement.getText();
        Assertions.assertThat(implicitWaitElement.isDisplayed()).isTrue();
        LOGGER.debug("finish element, implicitWaitElementText:[{}]", implicitWaitElementText);

        // Esperas - Explícitas WebDriverWait
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        driver.findElement(By.cssSelector("#start > button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        String text = element.getText();
        Assertions.assertThat(element.isDisplayed()).isTrue();
        LOGGER.debug("finish element, WebDriverWait, text:[{}]", text);

        driver.close();
        LOGGER.debug("driver closed");
    }

}
