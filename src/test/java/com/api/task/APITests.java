package com.api.task;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APITests {

    public final String BASE_URI = "https://jsonplaceholder.typicode.com";

    @Test
    public void testGetEndpoint(){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .get("/posts");

        String body = RestAssured.get().body().prettyPrint();
        System.out.println(body);

    }

    @Test
    public void testGetPost(){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1));

    }

    @Test
    public void testGetPostComments(){

        RestAssured.baseURI = BASE_URI;

        String comments = RestAssured.given().when().get("/posts/1/comments").prettyPrint();
        System.out.println(comments);
    }

    @Test
    public void testGetParameters(){

        RestAssured.baseURI = BASE_URI;

        String comments = RestAssured.given().when().get("/comments?postId=1").prettyPrint();
        System.out.println(comments);
    }

    @Test
    public void testPostEndpoint(){

        RestAssured.baseURI = BASE_URI;

        given()
                .header("Content-Type", "application/json")
                .body("{\"title\": \"task\", \"body\": \"SQATask\", \"userId\": 1}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("task"))
                .body("body", equalTo("SQATask"))
                .body("userId", equalTo(1));

    }

    @Test
    public void testPutEndpoint(){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .put("/posts/1");

    }

    @Test
    public void testPatchEndpoint(){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .patch("/posts/1");

    }

    @Test
    public void testDeleteEndpoint(){

        RestAssured.baseURI = BASE_URI;

        given()
                .when()
                .delete("/posts/1");

    }
}
