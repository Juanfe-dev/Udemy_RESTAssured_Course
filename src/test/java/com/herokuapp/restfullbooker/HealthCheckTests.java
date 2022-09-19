package com.herokuapp.restfullbooker;

import com.herokuapp.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTests extends BaseTest {


    @Test
    public void healthCheckTest(){

        //Chapter 18: RequestSpecification
        /* We will create a unique method to build this for all methods in BaseTest
        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .build();
        */
        given().
                spec(spec).
        when().
            get("/ping")
        .then()
            .assertThat()
            .statusCode(201);
    }
}
