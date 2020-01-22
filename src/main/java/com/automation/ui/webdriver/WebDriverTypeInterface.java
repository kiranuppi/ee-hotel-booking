package com.automation.ui.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Interface to get WebDriver object
 */
public interface WebDriverTypeInterface {
    WebDriver getWebDriverObject(DesiredCapabilities capabilities);
}
