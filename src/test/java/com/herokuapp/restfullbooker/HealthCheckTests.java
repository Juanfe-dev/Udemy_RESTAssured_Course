package com.herokuapp.restfullbooker;

import com.herokuapp.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
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
    @Test //CHAPTER 21: Headers and Cookies
    public void headersAndCookies(){
        //We don't have cookies to work.
        //We will do the encoding simulating that we have cookies.

        Header someHeader = new Header("Some name", "Some value");
        spec.header(someHeader);

        Cookie someCookie = new Cookie.Builder("Some Cookie", "Some Cookie Value").build();
        spec.cookie(someCookie);

        Response response = RestAssured.given(spec)
                .cookie("Test cookie name", "Test cookie value")
                .header("Test header name", "Test header value").log().all()
                .get("/ping");

        //Get Headers
        Headers headers = response.getHeaders();
        System.out.println("Headers: "+headers);

        //First way to print a specific header
        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());

        //Second way to print a specific header
        String serverHeader2 = response.getHeader("Server");
        System.out.println("Server: " + serverHeader2);

        //Get Cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies: "+ cookies);




    }
}
