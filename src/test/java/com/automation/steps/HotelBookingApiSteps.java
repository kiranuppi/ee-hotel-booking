package com.automation.steps;

import com.automation.api.HotelBooking;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.automation.util.TestProperties.PROPERTIES;
import static org.junit.Assert.assertEquals;

public class HotelBookingApiSteps {
    private static Logger logger = LoggerFactory.getLogger(HotelBookingApiSteps.class);
    private static String bookingId;
    private Response bookingResponse;
    private Response deleteResponse;

    private String firstName;
    private String surname;
    private String price;
    private String deposit;
    private String checkin;
    private String checkout;


    private HotelBooking hotelBooking() {

        String baseUrl = PROPERTIES.getProperty("hotelbooking.ui.base_url");
        return new HotelBooking(baseUrl + "/booking");

    }


    @Given("^I enter Firstname as \"([^\"]*)\" Surname as \"([^\"]*)\" Room Price as \"([^\"]*)\" Deposit as \"([^\"]*)\" Check-in as \"([^\"]*)\" and Check-out as \"([^\"]*)\" details$")
    public void bookingDetails(String firstname, String surname, String price, String deposit, String checkin, String checkout) throws Throwable {
        this.firstName = firstname;
        this.surname = surname;
        this.price = price;
        this.deposit = deposit;
        this.checkin = checkin;
        this.checkout = checkout;

    }


    @When("^I save the details$")
    public void iSaveTheDetails() throws Throwable {
        this.bookingResponse = hotelBooking().createBooking(firstName, surname, price, deposit, checkin, checkout);
        bookingId = bookingResponse.jsonPath().getString("bookingid");

    }

    @Then("^new booking should be created$")
    public void newBookingShouldBeCreated() throws Throwable {
        assertEquals(200, bookingResponse.getStatusCode());
    }

    @Given("^I have a valid booking ID$")
    public void iHaveAValidBookingID() throws Throwable {
        logger.debug("bookingId: " + bookingId);
    }

    @When("^I delete the booking$")
    public void iDeleteTheBooking() throws Throwable {
        deleteResponse = hotelBooking().deleteBooking(bookingId);

    }

    @Then("^Booking should be deleted$")
    public void bookingShouldBeDeleted() throws Throwable {
        assertEquals(201, deleteResponse.getStatusCode());

        //Try deleting the same booking should give you Method not allowed error as the booking ID doesnt exist
        // Error code could have been improved !
        deleteResponse = hotelBooking().deleteBooking(bookingId);
        assertEquals(405, deleteResponse.getStatusCode());
    }
}
