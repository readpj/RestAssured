package com.googlerestapi;

import com.jayway.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;


public class GetStatusCodeTest {
    @BeforeClass
    public void setBaseUri () {

        RestAssured.baseURI = "https://maps.googleapis.com";
    }

    @Test
    public void testStatusCode () {

        Response res =
                given ()
                        .param ("query", "restaurants in mumbai")
                        .param ("key", "AIzaSyDqIZq375kH96kofwoQylQjWK5DXO2JijQ")
                        .when()
                        .get ("/maps/api/place/textsearch/json");

        Assert.assertEquals (res.statusCode (), 200);
    }

    @Test
    public void testStatusCodeRestAssured () {

        given ().param ("query", "restaurants in mumbai")
                .param ("key", "AIzaSyDqIZq375kH96kofwoQylQjWK5DXO2JijQ")
                .when()
                .get ("/maps/api/place/textsearch/json")
                .then ()
                .assertThat ().statusCode (200);

    }

    @Test
    public void test01()  {
        Response res  =given ().param ("query", "pubs in cardiff")
                .param ("key", "AIzaSyDqIZq375kH96kofwoQylQjWK5DXO2JijQ")
                .when()
                .get ("/maps/api/place/textsearch/json")
    .then()
                .contentType(ContentType.JSON)
                .extract().response();


        System.out.println (res.asString ());

    }



}
