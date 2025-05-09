package com.api.task.utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Set;

public class ConfRequest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    /**
     * Get the response of HTTP GET method
     * @param endpoint
     * @return
     */
    public static Response getResponse(String endpoint){
        return RestAssured.get(BASE_URL + "/" + endpoint);
    }

    /**
     * Get the response of HTTP GET method with parameters
     * @param endpoint
     * @param params
     * @return
     */
    public static Response getResponse(String endpoint, String params){
        return RestAssured.get(BASE_URL + "/" + endpoint + "/" + params);
    }

    /**
     * Get the response of HTTP Get method by specifying parameters in URL
     * @param endpoint
     * @param params
     * @param value
     * @return
     */
    public static Response getResponseURLParams(String endpoint, String params, int value){
        return RestAssured.get(BASE_URL + "/" + endpoint + "?" + params + "=" + value);
    }

    /**
     * Get the response of HTTP GET method with parameters
     * @param endpoint
     * @return
     */
    public static Response getResponseComments(String endpoint, String params){
        return RestAssured.get(BASE_URL + "/" + endpoint + "/" + params + "/comments");
    }

    /**
     * Get the response of HTTP PUT method with parameters
     * @param endpoint
     * @param params
     * @return
     */
    public static Response getPutResponse(String endpoint, String params){
        return RestAssured.put(BASE_URL + "/" + endpoint + "/" + params);
    }

    /**
     * Get the response of HTTP PATCH method with parameters
     * @param endpoint
     * @param params
     * @return
     */
    public static Response getPatchResponse(String endpoint, String params){
        return RestAssured.patch(BASE_URL + "/" + endpoint + "/" + params);
    }

    /**
     * Get the response of HTTP DELETE method with parameters
     * @param endpoint
     * @param params
     * @return
     */
    public static Response getDeleteResponse(String endpoint, String params){
        return RestAssured.delete(BASE_URL + "/" + endpoint + "/" + params);
    }

    /**
     * Performs and gets the response of HTTP POST method
     * @param endpoint
     * @param bodyParams
     * @return
     */
    public static Response getPostResponse(String endpoint, HashMap bodyParams) {
        JSONObject jsonBuilder = new JSONObject();

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        Set<String> keys = bodyParams.keySet();

        for(String keyName : keys) {
            jsonBuilder.put(keyName, bodyParams.get(keyName));
        }

        request.header("Content-Type", "application/json");
        request.body(jsonBuilder.toString());

        return request.post("/" + endpoint);
    }

    /**
     * Validate the response code is as expected
     * @param response
     * @param responseCode
     */
    public static void validateResponseCode(Response response, int responseCode){
        if (response.getStatusCode() == responseCode)
            System.out.println("Status Code as expected: " + responseCode);
        else {
            Assert.fail();
            System.out.println("Status Code not as expected: \nActual: " + response.getStatusCode() + "\nExpected: " + responseCode);
        }
    }

    /**
     * Validates the response parameter is as expected
     * @param response
     * @param parameter
     * @param expected
     */
    public static void validateResponseParameter(Response response, String parameter, int expected){
        JsonPath jsonRes = response.jsonPath();
        if (jsonRes.get(parameter).equals(expected))
            System.out.println("Value of parameter " + parameter + " is as expected: " + expected);
        else {
            Assert.fail();
            System.out.println("Value of parameter " + parameter + " is not as expected: \nActual: " + jsonRes.get(parameter).toString() + "\nExpected: " + expected);
        }
    }

    /**
     * Validates the response parameter is as expected
     * @param response
     * @param parameter
     * @param expected
     */
    public static void validateResponseParameter(Response response, String parameter, String expected){
        JsonPath jsonRes = response.jsonPath();
        if (jsonRes.get(parameter).equals(expected))
            System.out.println("Value of parameter " + parameter + " is as expected: " + expected);
        else {
            Assert.fail();
            System.out.println("Value of parameter " + parameter + " is not as expected: \nActual: " + jsonRes.get(parameter).toString() + "\nExpected: " + expected);
        }
    }

}
