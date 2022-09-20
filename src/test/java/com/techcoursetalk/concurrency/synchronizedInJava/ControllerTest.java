package com.techcoursetalk.concurrency.synchronizedInJava;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class ControllerTest {

    public void 카운트_증가_요청(final String path) {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path);
    }

    public Integer 카운트_확인_요청(final String path){
        final ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then()
                .extract();

        return response.as(Integer.class);
    }

}
