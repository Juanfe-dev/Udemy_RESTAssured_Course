package com.herokuapp.restfullbooker;

import com.herokuapp.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTests extends BaseTest {

    @Test
    public void partialUpdateBookingTest(){

        //Create Json Body -> Create a booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get booking of the new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Create JSON Body
        JSONObject body = new JSONObject();
        body.put("firstname", "Isabel");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2022-10-16");
        bookingdates.put("checkout", "2022-10-30");

        //Add this line because we have to update the bookingdates object in the body
        body.put("bookingdates", bookingdates);

        //Partial Update booking
        //Change post for put
        //Autorization
        //    "username" : "admin",
        //    "password" : "password123"
        //Add .auth().preemptive().basic(username, password) for authentication
        Response responsePartialUpdate = RestAssured
                .given(spec).auth().preemptive().basic("admin","password123")
                .contentType(ContentType.JSON)
                .body(body.toString())//We have to convert to string first
                //.post("https://restful-booker.herokuapp.com/booking");
                //.put("https://restful-booker.herokuapp.com/booking/"+bookingid);
                .patch("/booking/"+bookingid);
        responsePartialUpdate.print();

        Assert.assertEquals(responsePartialUpdate.getStatusCode(), 200, "Expected 200 but it is not");

        //Verifications
        //Verify All fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = responsePartialUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName,"Isabel", "firstname in response is not expected");

        String actualLastName = responsePartialUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName,"Gomez Arboleda", "lastname in response is not expected");

        int actualTotalPrice = responsePartialUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualTotalPrice,6969, "totalprice in response is not expected");

        boolean actualDepositePaid = responsePartialUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(actualDepositePaid, "depositpaid should be false but it is not");

        String actualCheckin = responsePartialUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-10-16", "checkin in response is not expected");

        String actualCheckout = responsePartialUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-10-30", "checkout in response is not expected");

        String actualAdditionalNeeds = responsePartialUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Hamburguers", "additional needs in response is not expected");


        softAssert.assertAll();


    }

}
