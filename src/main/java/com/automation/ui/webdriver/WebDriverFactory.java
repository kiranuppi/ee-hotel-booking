package com.automation.ui.webdriver;

import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

import static com.automation.ui.webdriver.WebDriverType.CHROME;


/**
 * Creates WebDriver object and returns.
 * The type of WebDriver object depends on the environment variable: browser. The default value is: Chrome
 */
public class WebDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private WebDriverType selectedDriverType;
    private WebDriver webDriver;

    /*
    Read browser property from env variables.
    Initialize selectedDriverType property
     */
    public WebDriverFactory() {
        WebDriverType driverType = CHROME;
        String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        try {
            driverType = WebDriverType.valueOf(browser);
        } catch (Exception ignored) {
            logger.error("Driver type is not known. Defaulting to: {}", driverType);
        }
        selectedDriverType = driverType;
    }

    public WebDriver getWebDriver() {
        if (null == webDriver) {
            instantiateWebDriver(selectedDriverType);
        }
        return webDriver;
    }

    /*
    Construct WebDriver object
    if(remoteDriver = true) construct RemoteWebDriver object otherwise create normal WebDriver object
     */
    private void instantiateWebDriver(WebDriverType driverType) {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");

        // Create RemoteWebDriver object if remoteDriver=true
        if (BooleanUtils.toBoolean(useRemoteWebDriver)) {
            String gridURL = System.getProperty("gridURL");
            URL seleniumGridURL;
            try {
                seleniumGridURL = new URL(gridURL);
            } catch (MalformedURLException exc) {
                logger.error("Cannot connect to seleniumGridURL: {}", gridURL);
                throw new RuntimeException(String.format("Cannot connect to seleniumGridURL: %s", gridURL), exc);
            }

            String desiredPlatform = System.getProperty("desiredPlatform");
            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            desiredCapabilities.setBrowserName(selectedDriverType.toString().toLowerCase());
            webDriver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else {
            webDriver = driverType.getWebDriverObject(desiredCapabilities);
            webDriver.manage().deleteAllCookies();
            webDriver.manage().window().maximize();
        }
    }

    /*
    Quit webdriver
     */
    public void quitWebDriver() {
        if (webDriver != null) {
            webDriver.manage().deleteAllCookies();
            webDriver.quit();
            webDriver = null;
        }
    }

}
