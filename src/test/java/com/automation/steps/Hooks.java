package com.automation.steps;

import com.automation.ui.webdriver.WebDriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static Logger logger = LoggerFactory.getLogger(Hooks.class);

    /**
     * The static driver is initialised only once before first test
     * It is closed after all tests
     */
    private static WebDriver driver;

    static {
        Runtime.getRuntime().addShutdownHook(
                new Thread() {
                    @Override
                    public void run() {
                        if (driver != null) {
                            driver.manage().deleteAllCookies();
                            driver.quit();
                            driver = null;
                        }
                    }
                }
        );
    }

    static WebDriver getDriver() {
        return driver;
    }

    /**
     * This method is run before each test
     * Open browser before first test.
     */
    @Before("@ui")
    public void openDriver() {
        if (driver == null) {
            logger.debug("Creating driver");
            WebDriverFactory webDriverFactory = new WebDriverFactory();
            driver = webDriverFactory.getWebDriver();
            logger.debug("Driver created");
        }

    }

    /**
     * This method is run after each test
     * Delete all cookies from browser after each test
     * If test fails, save the browser screenshot to reports
     */
    @After("@ui")
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png"); // stick it in the report
            scenario.write("URL at failure: " + driver.getCurrentUrl());
        }

        driver.manage().deleteAllCookies();
    }
}
