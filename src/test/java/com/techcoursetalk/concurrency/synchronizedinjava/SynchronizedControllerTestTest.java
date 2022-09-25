package com.techcoursetalk.concurrency.synchronizedinjava;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
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
class SynchronizedControllerTestTest extends ControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("수강신청생 200명이 고스란히 반영된다.")
    void increaseSynchronizedCount() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(200);
        final CountDownLatch countDownLatch = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            executorService.submit(() -> {
                수강신청_후_폐강위험시_출력();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
    }

    private void 수강신청_후_폐강위험시_출력() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/synchronized/2/check-then-act");
    }
}
