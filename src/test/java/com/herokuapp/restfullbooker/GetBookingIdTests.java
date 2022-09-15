package com.herokuapp.restfullbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdTests {

    @Test
    public void getBookingIdsWithoutFilterTest(){

        // Get response with booking ids
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking");
        response.print();
        // Verify response 200
        Assert.assertEquals(response.getStatusCode(),
                200,
                "Status code should be 200 but it is not");
        // Verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty, but should not be" );
    }

}