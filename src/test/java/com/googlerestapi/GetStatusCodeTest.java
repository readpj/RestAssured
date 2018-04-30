package com.googlerestapi;

import com.jayway.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class GetStatusCodeTest {

    private String URI = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=pubs%20in%20cardiff&key=AIzaSyDqIZq375kH96kofwoQylQjWK5DXO2JijQ";
    private String key = "AIzaSyDqIZq375kH96kofwoQylQjWK5DXO2JijQ";

    @BeforeClass
    public void setBaseUri() {

        RestAssured.baseURI = "https://maps.googleapis.com";
    }

    @Test
    public void simpleTest() {
        given().when().get("https://maps.googleapis.com/maps/api/place/textsearch/json?query=pubs%20in%20cardiff&key=AIzaSyDqIZq375kH96kofwoQylQjWK5DXO2JijQ")
                .then().statusCode(200);
    }

    @Test
    public void zeroResults() {
        given().when().get(URI).then()
                .body(containsString("ZERO_RESULTS"));
    }

    @Test
    public void testStatusCode() {

        Response res = given()
                .param("query", "restaurants in cardiff")
                .param("key", key)
                .when()
                .get("/maps/api/place/textsearch/json");

        Assert.assertEquals(res.statusCode(), 200);
        System.out.println(res.getContentType());
    }

    @Test
    public void assertStatusCode() {

        given().param("query", "museums in cardiff")
                .param("key", key)
                .when()
                .get("/maps/api/place/textsearch/json")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void testBodyContainsString() {
        given().param("query", "pubs in Cardiff")
                .param("key", key)
                .when().get("/maps/api/place/textsearch/json")
                .then()
                .body("results[0].opening_hours.open_now",
                        equalTo(true))
                .statusCode(200);
    }

    @Test
    public void printResponse() {

        Response res = given().
                param("query", "pubs in cardiff")
                .param("key", key)
                .when()
                .get("/maps/api/place/textsearch/json")
                .then()
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("Status code is: " + res.statusCode());
        System.out.println(res.asString());
        res.getBody().print();

    }

    @Test
    public void testJsonPathBoolean() {
        Boolean res = given().param("query", "pubs in Cardiff")
                .param("key", key)
                .when()
                .get("/maps/api/place/textsearch/json")
                .then()
                .contentType(ContentType.JSON)
                .extract().path("results[0].opening_hours.open_now");

        System.out.println(res);

    }

    /**
     * This test fails...needs work
     **/
    @Test
    public void testJsonPathNumeric() {
        Float res = given().param("query", "pubs in Cardiff")
                .param("key", key)
                .when()
                .get("/maps/api/place/textsearch/json")
                .then()
                .contentType(ContentType.JSON)
                .extract().path("results[0].rating");

        Assert.assertEquals(res, 4.4);
        System.out.println(res);

    }

}
