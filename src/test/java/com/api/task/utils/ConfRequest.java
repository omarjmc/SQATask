package com.api.task.utils;

import groovy.json.JsonBuilder;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class ConfRequest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static Response getResponse(String endpoint){
        return RestAssured.get(BASE_URL + "/" + endpoint);
    }

    public static Response getResponse(String endpoint, String params){
        return RestAssured.get(BASE_URL + "/" + endpoint + "/" + params);
    }

    public static Response getResponseURLParams(String endpoint, String params, int value){
        return RestAssured.get(BASE_URL + "/" + endpoint + "?" + params + "=" + value);
    }

    public static Response getResponseComments(String endpoint, String params){
        return RestAssured.get(BASE_URL + "/" + endpoint + "/" + params + "/comments");
    }

    public static Response getPutResponse(String endpoint, String params){
        return RestAssured.put(BASE_URL + "/" + endpoint + "/" + params);
    }

    public static Response getPatchResponse(String endpoint, String params){
        return RestAssured.patch(BASE_URL + "/" + endpoint + "/" + params);
    }

    public static Response getDeleteResponse(String endpoint, String params){
        return RestAssured.delete(BASE_URL + "/" + endpoint + "/" + params);
    }

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

    public static void validateResponseCode(Response response, int responseCode){
        if (response.getStatusCode() == responseCode)
            System.out.println("Status Code as expected: " + responseCode);
        else
            System.out.println("Status Code not as expected: \nActual: " + response.getStatusCode() + "\nExpected: " + responseCode);
    }

    public static void validateResponseParameter(Response response, String parameter, int expected){
        JsonPath jsonRes = response.jsonPath();
        if (jsonRes.get(parameter).equals(expected))
            System.out.println("Value of parameter " + parameter + " is as expected: " + expected);
        else
            System.out.println("Value of parameter " + parameter + " is not as expected: \nActual: " + jsonRes.get(parameter).toString() + "\nExpected: " + expected);
    }

    public static void validateResponseParameter(Response response, String parameter, String expected){
        JsonPath jsonRes = response.jsonPath();
        if (jsonRes.get(parameter).equals(expected))
            System.out.println("Value of parameter " + parameter + " is as expected: " + expected);
        else
            System.out.println("Value of parameter " + parameter + " is not as expected: \nActual: " + jsonRes.get(parameter).toString() + "\nExpected: " + expected);
    }

}
