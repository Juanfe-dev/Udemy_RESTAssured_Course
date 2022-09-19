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

        //Create JSON Body
        JSONObject body = new JSONObject();
        body.put("firstname", "Amalia");
        body.put("lastname", "Arboleda");
        body.put("totalprice", 100);
        body.put("depositpaid", true);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2022-09-15");
        bookingdates.put("checkout", "2022-09-20");
        body.put("bookingdates", bookingdates);

        body.put("additionalneeds","Avocado");

        //Update booking
        //Change post for put
        //Autorization
        //    "username" : "admin",
        //    "password" : "password123"
        //Add .auth().preemptive().basic(username, password) for authentication
        Response responseUpdate = RestAssured
                .given(spec).auth().preemptive().basic("admin","password123")
                .contentType(ContentType.JSON)
                .body(body.toString()) //We have to convert to string first
                //.post("https://restful-booker.herokuapp.com/booking");
                .put("/booking/"+bookingid);

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

        String actualAdditionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Avocado", "additional needs in response is not expected");


        softAssert.assertAll();


    }


}
