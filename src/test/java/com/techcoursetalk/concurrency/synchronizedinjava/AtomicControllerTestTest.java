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
class AtomicControllerTestTest extends ControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Atomic 변수 적용")
    void increaseAtomicCount() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(30);
        final CountDownLatch countDownLatch = new CountDownLatch(30);
        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> {
                수강_신청("/atomic/increase");
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        final Integer count = 수강생_수_확인("/atomic/count");

        System.out.println("count = " + count);
        assertThat(count).isEqualTo(30);
    }
}
