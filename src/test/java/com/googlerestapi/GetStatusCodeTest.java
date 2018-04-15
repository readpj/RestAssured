package com.googlerestapi;

import com.jayway.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;


public class GetStatusCodeTest {

    private String key = "AIzaSyDqIZq375kH96kofwoQylQjWK5DXO2JijQ";

    @BeforeClass
    public void setBaseUri() {

        RestAssured.baseURI = "https://maps.googleapis.com";
    }

    @Test
    public void testStatusCode() {

        Response res =
                given()
                        .param("query", "restaurants in mumbai")
                        .param("key", key)
                        .when()
                        .get("/maps/api/place/textsearch/json");

        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test
    public void testStatusCodeRestAssured() {

        given().param("query", "restaurants in mumbai")
                .param("key", key)
                .when()
                .get("/maps/api/place/textsearch/json")
                .then()
                .assertThat().statusCode(200);

    }

    @Test
    public void test01() {
        /* Get https://maps.googleapis.com/maps/api/place/textsearch/
        /** json?query=pubs%20in%20cardiff&key=AIzaSyBrhdZP1wWpMXVEvzpY4-3W-FKieCYhVXg" **/

        Response res = given().param("query", "pubs in cardiff")
                .param("key", key)
                .when()
                .get("/maps/api/place/textsearch/json")
                .then()
                .contentType(ContentType.JSON)
                .extract().response();


        System.out.println(res.asString());
        System.out.println("Status code is: " + res.statusCode());
        res.getBody().print();

    }

    @Test
    public void testJsonPath()  {
        Response res  =given ().param ("query", "pubs in Cardiff")
                .param ("key", key)
                .when()
                .get ("/maps/api/place/textsearch/json")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .path ("results[0].formatted_address");

        Assert.assertEquals (res, "25 St. Mary's Street, Cardiff CF10 1AA, UK");

    }



}
