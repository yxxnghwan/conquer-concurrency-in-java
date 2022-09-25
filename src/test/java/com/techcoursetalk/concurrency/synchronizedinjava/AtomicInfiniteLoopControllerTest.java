package com.techcoursetalk.concurrency.synchronizedinjava;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AtomicInfiniteLoopControllerTest extends ControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Atomic 무한")
    void increaseAtomicCount() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(200);
        final CountDownLatch countDownLatch = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            executorService.submit(() -> {
                수강_신청("/atomic/crush/increase");
                countDownLatch.countDown();
            });
        }

        countDownLatch.await(5000, TimeUnit.MILLISECONDS);

        if (countDownLatch.getCount() == 0) {
            throw new RuntimeException("데드락이 발생하지 않았습니다.");
        }
    }
}
