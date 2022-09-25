package com.techcoursetalk.concurrency.racecondition;


import static org.assertj.core.api.Assertions.*;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RaceConditionTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("read-modify-write 경쟁조건 테스트: 수강생이 30명이 됐는데 수강생 기준 미달이 돼서 강의가 폐강됐다.")
    void multiThreadIncrease() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(30);
        final CountDownLatch countDownLatch = new CountDownLatch(30);
        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> {
                수강_신청();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        final Integer count = 수강생_수_확인();

        System.out.println("count = " + count);
        assertThat(count).isNotEqualTo(30);
    }

    @Test
    @DisplayName("check-then-act 경쟁조건 테스트")
    void printWarning() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                수강신청_후_폐강위험시_출력();
            });
        }

        Thread.sleep(1500);
    }

    private void 수강신청_후_폐강위험시_출력() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/race-condition/2/check-then-act");
    }

    private Integer 수강생_수_확인() {
        final ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/race-condition/1/count")
                .then()
                .extract();

        return response.as(Integer.class);
    }

    private void 수강_신청() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/race-condition/1/increase");
    }
}