package com.herokuapp.restfullbooker;

import com.herokuapp.restfulbooker.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTests extends BaseTest {

    @Test
    public void createBookintTest(){

        //Create Json Body -> Create a booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get booking of the new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Delete booking
        //Change post for put
        //Autorization
        //    "username" : "admin",
        //    "password" : "password123"
        //Add .auth().preemptive().basic(username, password) for authentication
        Response responsePartialUpdate = RestAssured
                .given(spec).auth().preemptive().basic("admin","password123")
                //.post("https://restful-booker.herokuapp.com/booking");
                //.put("https://restful-booker.herokuapp.com/booking/"+bookingid);
                //.patch("https://restful-booker.herokuapp.com/booking/"+bookingid);
                .delete("/booking/"+bookingid);
        responsePartialUpdate.print();

        //Verifications
            //Verify response 201
            Assert.assertEquals(responsePartialUpdate.getStatusCode(), 201, "Expected 201 but it is not");

            //Response the endpoint
            Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/"+bookingid);
            responseGet.print();

            //Response body as string
            Assert.assertEquals(responseGet.getBody().asString(),
                    "Not Found",
                    "Body should be 'Not Found'");
    }


}
