package com.herokuapp.restfullbooker;

import com.herokuapp.restfulbooker.BaseTest;
import com.herokuapp.restfulbooker.Booking;
import com.herokuapp.restfulbooker.Bookingdates;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest {

    @Test (enabled = false)
    public void createBookingTest(){

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


        //Get response
        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(body.toString()) //We have to convert to string first
                .post("/booking");

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

    @Test
    public void createBookingWithPOJOTest(){

        //create body using POJOs
        Bookingdates  bookingdates = new Bookingdates("2022-09-19", "2022-09-27");
        Booking booking = new Booking(
                "Mariana",
                "Palacios",
                500,
                false,
                bookingdates,
                "Chocolate");

        //Get response
        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(booking)
                .post("/booking");
        response.print();

        //Verifications
        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 but it is not");

        //Verify All fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName,"Mariana", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName,"Palacios", "lastname in response is not expected");

        int actualTotalPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice,500, "totalprice in response is not expected");

        boolean actualDepositePaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(actualDepositePaid, "depositpaid should be false but it is not");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-09-19", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-09-27", "checkout in response is not expected");

        String actualAdditionalNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds,"Chocolate", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }
}
