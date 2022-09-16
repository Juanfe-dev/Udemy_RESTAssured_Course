package com.herokuapp.restfullbooker;

import com.herokuapp.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTests extends BaseTest {

    @Test
    public void updateBookintTest(){

        //Create Json Body -> Create a booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get booking of the new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Update booking
        JSONObject body = new JSONObject();
        body.put("firstname", "Amalia");
        body.put("lastname", "Arboleda");
        body.put("totalprice", 100);
        body.put("depositpaid", true);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2022-09-15");
        bookingdates.put("checkout", "2022-09-20");
        body.put("bookingdates", bookingdates);

        body.put("additionalneeds","Hamburguers");

        //Change post for put
        Response responseUpdate = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body.toString()) //We have to convert to string first
                //.post("https://restful-booker.herokuapp.com/booking");
                .put("https://restful-booker.herokuapp.com/booking/"+bookingid);

        responseUpdate.print();

        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Expected 200 but it is not");

        //Verifications
            //Verify All fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName,"Amalia", "firstname in response is not expected");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName,"Arboleda", "lastname in response is not expected");

        int actualTotalPrice = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualTotalPrice,100, "totalprice in response is not expected");

        boolean actualDepositePaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(actualDepositePaid, "depositpaid should be false but it is not");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-09-15", "checkin in response is not expected");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-09-20", "checkout in response is not expected");

        softAssert.assertAll();


    }


}
