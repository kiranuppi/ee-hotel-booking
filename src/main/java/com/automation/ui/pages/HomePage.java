package com.automation.ui.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Home page
 */
public class HomePage extends BasePage {

    @FindBy(how = How.CSS, using = "#firstname")
    private WebElement txt_firstname;

    @FindBy(how = How.CSS, using = "#lastname")
    private WebElement txt_surname;

    @FindBy(how = How.CSS, using = "#totalprice")
    private WebElement txt_price;

    @FindBy(how = How.CSS, using = "#depositpaid")
    private WebElement drpdwn_depositPaid;

    @FindBy(how = How.CSS, using = "#checkin")
    private WebElement txt_checkInDate;

    @FindBy(how = How.CSS, using = "#checkout")
    private WebElement txt_checkoutDate;

    @FindBy(how = How.XPATH, using = "//input[@value=' Save ']")
    private WebElement btn_submit;


    @FindBy(how = How.XPATH, using = "//div[@class='row']")
    private List<WebElement> bookings;


    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void open(String baseUrl) {
        String pageUrl = baseUrl;
        webDriver.get(pageUrl);
    }


    public void enterBookingDetails(String firstName, String lastName, String price, String depositPaid, String checkIn, String checkout) {
        Select select = new Select(drpdwn_depositPaid);
        txt_firstname.sendKeys(firstName);
        txt_surname.sendKeys(lastName);
        txt_price.sendKeys(price);
        select.selectByVisibleText(depositPaid);
        txt_checkInDate.sendKeys(checkIn);
        txt_checkoutDate.sendKeys(checkout + Keys.TAB);

    }

    public void saveNewBooking() {
        btn_submit.click();
        waitForJSandJQueryToLoad();
        waitForAjaxCallToFinish();

    }


    public List<WebElement> getBookingDetails() {
        waitForJSandJQueryToLoad();
        bookings.size();
        return bookings;
    }


    public void deleteBooking(int rowNum) {
        waitForJSandJQueryToLoad();
        WebElement element = webDriver.findElement(By.xpath("//div[@id='bookings']/div[" + rowNum + "]//input"));
        waitForJQueryLoad();
        element.click();
    }
}
