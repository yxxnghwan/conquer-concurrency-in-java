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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoSynchronizedControllerTestTest extends ControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("동기화 미적용")
    void increaseNoSynchronizedCount() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(200);
        final CountDownLatch countDownLatch = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            executorService.submit(() -> {
                카운트_증가_요청("/no-synchronized/increase");
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        final Integer count = 카운트_확인_요청("/no-synchronized/count");

        System.out.println("count = " + count);
        assertThat(count).isNotEqualTo(200);
    }
}
