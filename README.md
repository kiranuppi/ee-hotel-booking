### Hotel Booking Automation Tests (Both UI and API tests)

This project contains API and GUI automation tests for Hotel Booking form.

#### Tools Used
- Java
- Selenium
- Webdrivermanager
- RestAssured
- Junit
- Maven
- AssertJ
- Log4j2

#### How to configure environment:
- Configure "hotelbooking.ui.base_url" in: {ProjectHome}/src/test/resources/environment/dev.properties and qa.properties
- Default environment is: dev. To change default environment: Update "environment" property in pom.xml (propertyName: environment) 
- OR pass the environment variable via java runtime args (Ex: mvn clean test -Denvironment=dev)

#### How to run tests

- To run api and ui tests in default environment: mvn clean test
- To run only api tests: mvn clean test -Dcucumber.options="--tags @api"
- To run only ui tests: mvn clean test -Dcucumber.options="--tags @ui"

##### To run tests on Chrome
- mvn clean test -Dbrowser=Chrome

##### To run tests on Firefox
- mvn clean test -Dbrowser=Firefox

##### To run tests in headless mode
- mvn clean test -Dbrowser=Chrome -Dheadless=true

##### To run tests in different environment. Ex: qa
- mvn clean test -Denvironment=qa


#### Test reports
- Test reports can be found at: {ProjectHome}/target/reports/index.html

#### Test Runs
- All tests passed in Mac on Chrome (Version 78.0.3904.108) !
- Sample test report for Chrome is saved to: {ProjectHome}\test_report(chrome).png


#### Improvements to do
- In UI tests add more assertions to validate details of create and delete operations
- Use SpringFramework to share the state between steps and create browser
- Update tests so that they can run in Chrome Emulator (Apple iPhone X, Android Pixel 2).


