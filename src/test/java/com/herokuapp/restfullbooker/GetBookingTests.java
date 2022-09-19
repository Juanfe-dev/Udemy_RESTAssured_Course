package com.herokuapp.restfullbooker;

import com.herokuapp.restfulbooker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class GetBookingTests extends BaseTest {

    //CHAPTER 11 HOMEWORK
    /* @Test
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
    }*/
    @Test (enabled = true)
    public void getBookingTests(){

        /*
        //We will create a unique method to build this for all methods in BaseTest
        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .build();
        */
        //CHAPTER 19: Set path parameter
        //We will replace the path of the next line for the parameter

        //Create Json Body -> Create a booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get response with booking
        //int bookingid = responseCreate.jsonPath().getInt("bookingid");
        //  - - - - - - - - - - - - - - ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
        spec.pathParams("bookingId", responseCreate.jsonPath().getInt("bookingid"));
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        //Response response = RestAssured.given(spec).get("/booking/5");
        response.print();

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 but it is not");
        //Verify All fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName,"Juan Felipe", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName,"Gomez Arboleda", "lastname in response is not expected");

        int actualTotalPrice = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualTotalPrice,6969, "totalprice in response is not expected");

        boolean actualDepositePaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(actualDepositePaid, "depositpaid should be true but it is not");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-09-15", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-09-20", "checkout in response is not expected");

        softAssert.assertAll();

        /* CHAPTER 11 */
        /*{
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
        }*/

    }

    @Test //CHAPTER 22: XML Response
    public void getBookingXMLTests(){

        //Get response with booking
        Header xml = new Header("Accept", "application/xml");
        spec.header(xml);

        Response responseCreate = createBooking();
        responseCreate.print();

        //Set path parameter
        spec.pathParams("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        //Get response with booking
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 but it is not");

        //Verify All fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.xmlPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName,"Juan Felipe", "firstname in response is not expected");

        String actualLastName = response.xmlPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName,"Gomez Arboleda", "lastname in response is not expected");

        int actualTotalPrice = response.xmlPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice,6969, "totalprice in response is not expected");

        boolean actualDepositePaid = response.xmlPath().getBoolean("depositpaid");
        softAssert.assertFalse(actualDepositePaid, "depositpaid should be true but it is not");

        String actualCheckin = response.xmlPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-09-15", "checkin in response is not expected");

        String actualCheckout = response.xmlPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-09-20", "checkout in response is not expected");

        softAssert.assertAll();
    }
}
