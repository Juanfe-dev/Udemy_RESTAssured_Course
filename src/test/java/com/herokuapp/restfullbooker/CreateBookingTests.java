package com.herokuapp.restfullbooker;

import com.herokuapp.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest {

    @Test
    public void createBookintTest(){

        //Create Json Body
        //Import dependency Json in java

        Response response = createBooking();

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
