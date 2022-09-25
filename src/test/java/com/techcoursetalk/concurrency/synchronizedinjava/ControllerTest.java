package com.techcoursetalk.concurrency.synchronizedinjava;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class ControllerTest {

    public void 수강_신청(final String path) {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(path);
    }

    public Integer 수강생_수_확인(final String path){
        final ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then()
                .extract();

        return response.as(Integer.class);
    }
}
