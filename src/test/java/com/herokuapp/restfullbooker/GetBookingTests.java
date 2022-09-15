package com.herokuapp.restfullbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class GetBookingTests {

    @Test //CHAPTER 11 HOMEWORK
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
                "The lastname should be 'Morante Briones' but it is not");
    }
    @Test
    public void getBookingTests(){
        // Get response with booking
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/210");
        response.print();
        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 but it is not");
        //Verify All fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName,"Howard", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName,"Morante Briones", "lastname in response is not expected");

        int actualTotalPrice = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualTotalPrice,111, "totalprice in response is not expected");

        boolean actualDepositePaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(actualDepositePaid, "depositpaid should be true but it is not");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2018-01-01", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2019-01-01", "checkout in response is not expected");

        softAssert.assertAll();

        /*
        {
            "firstname":"Howard",
            "lastname":"Morante Briones",
            "totalprice":111,
            "depositpaid":true,
            "bookingdates":
                {
                    "checkin":"2018-01-01",
                    "checkout":"2019-01-01"
                },
            "additionalneeds":"Breakfast"
        }
         */
    }
}
