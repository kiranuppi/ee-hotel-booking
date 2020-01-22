package com.automation.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

/**
 * Rest client for Hotel Booking api
 * http://hotel-test.equalexperts.io/booking/.
 */
public class HotelBooking {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    public HotelBooking(String baseUri) {
        this.requestSpec = new RequestSpecBuilder().setBaseUri(baseUri)
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON).build();
        this.responseSpec = new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }

    public Response createBooking(String firstname, String surname, String price, String deposit, String checkin, String checkout) {

        String payload = "\n" +
                "{\"firstname\":\"" + firstname + "\"," +
                "\"lastname\":\"" + surname + "\"," +
                "\"totalprice\":\"" + price + "\"," +
                "\"depositpaid\":\"" + deposit + "\"," +
                "\"bookingdates\":{\"checkin\":\"" + checkin + "\",\"checkout\":\"" + checkout + "\"}}";


        return given().spec(requestSpec).
                body(payload)
                .when().post("")
                .then().spec(responseSpec).extract().response();
    }

    public Response deleteBooking(String bookingId) {
        return given().
                header("authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=").
                spec(requestSpec).
                contentType(ContentType.ANY).
                when().delete("/" + bookingId)
                .then().spec(responseSpec).extract().response();
    }

}
