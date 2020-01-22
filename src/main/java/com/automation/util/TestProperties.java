package com.automation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Enum which contains data from property files
 * Contains method to return value for a property.
 */
public enum TestProperties {

    PROPERTIES;

    private static final Logger logger = LoggerFactory.getLogger(TestProperties.class);
    private final Properties properties;

    TestProperties() {
        properties = new Properties();

        // load default properties (contains environment-name)
        loadPropertyFile(properties, "default.properties");

        // load environment specific properties
        System.out.println("Environment:" + properties.getProperty("environment"));
        String environmentFilePath = "environment/" + properties.getProperty("environment") + ".properties";
        loadPropertyFile(properties, environmentFilePath);
    }

    private static void loadPropertyFile(Properties properties, String fileName) {
        try (InputStream inputStream = TestProperties.class.getClassLoader().getResourceAsStream(fileName)) {
            System.out.println("Loading properties from file:" + fileName);
            properties.load(inputStream);
        } catch (Exception exc) {
            throw new RuntimeException(String.format("Property file: %s could not be loaded", fileName));
        }
    }

    public String getProperty(String propertyName) {
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue == null) {
            throw new RuntimeException(String.format("propertyValue is not found for propertyName: %s", propertyName));
        }
        return propertyValue;
    }

    public String getProperty(String propertyName, String defaultValue) {
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue == null) {
            logger.warn(String.format("propertyValue is not found for propertyName: %s, using defaultValue: %s", propertyName, defaultValue));
            propertyValue = defaultValue;
        }
        return propertyValue;
    }
}
