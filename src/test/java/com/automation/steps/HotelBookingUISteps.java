package com.automation.steps;

import com.automation.ui.pages.HomePage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.automation.util.TestProperties.PROPERTIES;
import static org.assertj.core.api.Assertions.assertThat;

public class HotelBookingUISteps {

    private static Logger logger = LoggerFactory.getLogger(HotelBookingUISteps.class);

    private HomePage homePage;

    private static int currentBookingCount;

    private static int bookingCountAfterNewBooking;

    @Given("I am on Hotel Booking Home page")
    public void i_am_on_HotelBooking_Home_page() {
        String url = PROPERTIES.getProperty("hotelbooking.ui.base_url");
        logger.debug("url: " + url);
        WebDriver webDriver = Hooks.getDriver();
        homePage = new HomePage(webDriver);
        homePage.open(url);

    }

    @Given("^I enter Firstname as \"([^\"]*)\" Surname as \"([^\"]*)\" Price as \"([^\"]*)\" Deposit as \"([^\"]*)\" Check-in date as \"([^\"]*)\" and Check-out date as \"([^\"]*)\" details$")
    public void i_enter_booking_details(String firstName, String lastName, String price, String depositPaid, String checkIn, String checkout) {
        homePage.enterBookingDetails(firstName, lastName, price, depositPaid, checkIn, checkout);
    }

    @When("^I Click on save button$")
    public void iClickOnSaveButton() {
        currentBookingCount = homePage.getBookingDetails().size() - 1;
        homePage.saveNewBooking();
    }

    @Then("^new booking is created$")
    public void newBookingIsCreated() {

        homePage.getBookingDetails();
        bookingCountAfterNewBooking = homePage.getBookingDetails().size();
        assertThat(bookingCountAfterNewBooking).isGreaterThan(currentBookingCount);

    }

    @When("^I click on delete button$")
    public void iClickOnDeleteButton() {
        homePage.deleteBooking(currentBookingCount);

    }

    @Then("^Booking is deleted$")
    public void bookingIsDeleted() {
        assertThat(homePage.getBookingDetails().size()).isEqualTo(bookingCountAfterNewBooking);

    }


}
