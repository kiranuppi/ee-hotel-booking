package com.automation.ui.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for WebDrivers of different browsers
 * Creates WebDriver object with various options
 */
public enum WebDriverType implements WebDriverTypeInterface {

    CHROME {
        @Override
        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.merge(capabilities);
            chromeOptions.setHeadless(HEADLESS);
            chromeOptions.addArguments("--no-default-browser-check");

            if ("EMULATOR".equalsIgnoreCase(System.getProperty("webDriverType"))) {
                Map<String, String> mobileEmulation = new HashMap<>();
                String deviceName = System.getProperty("webDriverMobileDevice");
                mobileEmulation.put("deviceName", deviceName);
                System.out.println("Emulator deviceName: " + deviceName);
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            } else {
                HashMap<String, Object> chromePreferences = new HashMap<>();
                chromePreferences.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", chromePreferences);
            }

            return new ChromeDriver(chromeOptions);
        }
    },

    FIREFOX {
        @Override
        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            WebDriverManager.firefoxdriver().setup();

            FirefoxOptions options = new FirefoxOptions();
            options.merge(capabilities);
            options.setHeadless(HEADLESS);
            return new FirefoxDriver(options);
        }
    };

    public final static boolean HEADLESS = Boolean.getBoolean("headless");
}
