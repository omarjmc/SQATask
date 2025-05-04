package com.api.task;

import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APITests {

    public final String BASE_URI = "https://jsonplaceholder.typicode.com";

    @DataProvider(name = "parameters")
    public Object[][] endpointParams(){
        return new Object[][] {{1}, {2}};
    }

    @DataProvider(name = "postMethod")
    public Object[][] postParameters(){
        return new Object[][] {{"task", "SQATask", 1}, {"foo", "bar", 1}};
    }

    @Test
    public void testGetEndpoint(){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .get("/posts");

        String body = RestAssured.get().body().prettyPrint();
        System.out.println(body);

    }

    @Test(dataProvider = "parameters")
    public void testGetPost(int val){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .get("/posts/" + String.valueOf(val))
                .then()
                .statusCode(200)
                .body("id", equalTo(val))
                .body("userId", equalTo(1));

    }

    @Test(dataProvider = "parameters")
    public void testGetPostComments(int val){

        RestAssured.baseURI = BASE_URI;

        String comments = RestAssured.given().when().get("/posts/" + String.valueOf(val) + "/comments").prettyPrint();
        System.out.println(comments);
    }

    @Test(dataProvider = "parameters")
    public void testGetParameters(int val){

        RestAssured.baseURI = BASE_URI;

        String comments = RestAssured.given().when().get("/comments?postId=" + String.valueOf(val)).prettyPrint();
        System.out.println(comments);
    }

    @Test(dataProvider = "postMethod")
    public void testPostEndpoint(String title, String body, int id){

        RestAssured.baseURI = BASE_URI;

        given()
                .header("Content-Type", "application/json")
                .body("{\"title\": \""+ title +"\", \"body\": \""+ body +"\", \"userId\": "+ id +"}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo(title))
                .body("body", equalTo(body))
                .body("userId", equalTo(1));

    }

    @Test(dataProvider = "parameters")
    public void testPutEndpoint(int val){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .put("/posts/" + String.valueOf(val));

    }

    @Test(dataProvider = "parameters")
    public void testPatchEndpoint(int val){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .patch("/posts/" + String.valueOf(val));

    }

    @Test(dataProvider = "parameters")
    public void testDeleteEndpoint(int val){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .delete("/posts/" + String.valueOf(val));

    }
}
