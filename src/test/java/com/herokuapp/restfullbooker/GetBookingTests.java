package com.herokuapp.restfullbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
/*
        CHAPTER 11 HOMEWORK
 */
public class GetBookingTests {

    @Test
    public void getBookingNameAndLastName(){

        // Get response from GetBooking

        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/210");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not" );

        // Verify the name and lastname in the GetBooking endpoint

        Assert.assertEquals(response.jsonPath().getString("firstname"),
                "Howard",
                "The firstname should be 'Howards' but it is not");

        Assert.assertEquals(response.jsonPath().getString("lastname"),
                "Morante Briones",
                "The lasname should be 'Morante Briones' but it is not");
    }
}
