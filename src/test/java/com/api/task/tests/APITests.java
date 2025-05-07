package com.api.task.tests;

import com.api.task.utils.ConfRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

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

        String body = ConfRequest.getResponse("posts").asPrettyString();
        System.out.println(body);

    }

    @Test(dataProvider = "parameters")
    public void testGetPost(int val){

        Response res = ConfRequest.getResponse("posts", "1");
        ConfRequest.validateResponseCode(res, 200);
        ConfRequest.validateResponseParameter(res, "id", val);
        ConfRequest.validateResponseParameter(res, "userId", 1);

    }

    @Test(dataProvider = "parameters")
    public void testGetPostComments(int val){

        String comments = ConfRequest.getResponseComments("posts", String.valueOf(val)).prettyPrint();
        System.out.println(comments);
    }

    @Test(dataProvider = "parameters")
    public void testGetParameters(int val){

        String comments = ConfRequest.getResponseURLParams("comments", "postId", val).prettyPrint();
        System.out.println(comments);
    }

    @Test(dataProvider = "postMethod")
    public void testPostEndpoint(String title, String body, int id){

        HashMap<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("userId", String.valueOf(id));

        Response response = ConfRequest.getPostResponse("posts", params);
        ConfRequest.validateResponseCode(response, 201);
        ConfRequest.validateResponseParameter(response, "title", title);
        ConfRequest.validateResponseParameter(response, "body", body);
        ConfRequest.validateResponseParameter(response, "userId", id);

    }

    @Test(dataProvider = "parameters")
    public void testPutEndpoint(int val){

        Response res = ConfRequest.getPutResponse("posts", String.valueOf(val));
        System.out.println(res.prettyPrint());

    }

    @Test(dataProvider = "parameters")
    public void testPatchEndpoint(int val){

        Response res = ConfRequest.getPatchResponse("posts", String.valueOf(val));
        System.out.println(res.prettyPrint());


    }

    @Test(dataProvider = "parameters")
    public void testDeleteEndpoint(int val){

        Response res = ConfRequest.getDeleteResponse("posts", String.valueOf(val));
        System.out.println(res.prettyPrint());

    }
}
