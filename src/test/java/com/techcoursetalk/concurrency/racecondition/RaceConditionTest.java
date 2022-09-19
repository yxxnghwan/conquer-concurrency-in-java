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
    @DisplayName("read-modify-write 경쟁조 테스트")
    void multiThreadIncrease() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(200);
        final CountDownLatch countDownLatch = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            executorService.submit(() -> {
                카운트_증가_요청();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        final Integer count = 카운트_확인_요청();

        System.out.println("count = " + count);
        assertThat(count).isNotEqualTo(200);
    }

    @Test
    @DisplayName("check-then-act 경쟁조건 테스트")
    void printOdd() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                홀수만_출력();
            });
        }

        Thread.sleep(1500);
    }

    private void 홀수만_출력() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/race-condition/2/check-then-act");
    }

    private Integer 카운트_확인_요청() {
        final ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/race-condition/1/count")
                .then()
                .extract();

        return response.as(Integer.class);
    }

    private void 카운트_증가_요청() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/race-condition/1/increase");
    }
}