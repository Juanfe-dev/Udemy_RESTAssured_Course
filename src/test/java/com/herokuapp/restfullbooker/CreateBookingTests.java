package com.herokuapp.restfullbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests {

    @Test
    public void createBookintTest(){

        //Create Json Body
        //Import dependency Json in java

        JSONObject body = new JSONObject();
        body.put("firstname", "Juan Felipe");
        body.put("lastname", "Gomez Arboleda");
        body.put("totalprice", 6969);
        body.put("depositpaid", false);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2022-09-15");
        bookingdates.put("checkout", "2022-09-20");
        body.put("bookingdates", bookingdates);

        body.put("additionalneeds","Hamburguers");

        /* EXAMPLE:
            "firstname" : "Jim",
            "lastname" : "Brown",
            "totalprice" : 111,
            "depositpaid" : true,
            "bookingdates" : {
                                "checkin" : "2018-01-01",
                                "checkout" : "2019-01-01"
                             },
            "additionalneeds" : "Breakfast"
         */

        //Get response
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body.toString()) //We have to convert to string first
                .post("https://restful-booker.herokuapp.com/booking");

        response.print();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 but it is not");

        //Verifications
            //Verify All fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName,"Juan Felipe", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName,"Gomez Arboleda", "lastname in response is not expected");

        int actualTotalPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice,6969, "totalprice in response is not expected");

        boolean actualDepositePaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(actualDepositePaid, "depositpaid should be false but it is not");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-09-15", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-09-20", "checkout in response is not expected");

        softAssert.assertAll();


    }
}
