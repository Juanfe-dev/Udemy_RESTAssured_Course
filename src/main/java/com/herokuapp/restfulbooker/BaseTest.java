package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected RequestSpecification spec;
    @BeforeMethod
    public void setUp(){
         spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .build();
    }

    protected Response createBooking() {
        //Import dependency Json in java

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
                .given()
                .contentType(ContentType.JSON)
                .body(body.toString()) //We have to convert to string first
                .post("https://restful-booker.herokuapp.com/booking");
                return response;

    }
}
