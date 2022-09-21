package com.techcoursetalk.concurrency.deadlock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeadLockExTest {

    @Test
    @DisplayName("addAAndGetSum과 addBAndGetSum을 연달아 여러 번 요청해서 데드락을 발생시킨다. ")
    void deadlock() throws InterruptedException {
        final DeadLockEx deadLockEx = new DeadLockEx();
        final ExecutorService executorService = Executors.newFixedThreadPool(1000);
        final CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            final Runnable task = createTask(deadLockEx, countDownLatch, i);
            executorService.submit(task);
        }

        countDownLatch.await(5000, TimeUnit.MILLISECONDS);

        if (countDownLatch.getCount() == 0) {
            throw new RuntimeException("데드락이 발생하지 않았습니다.");
        }
    }

    private Runnable createTask(final DeadLockEx deadLockEx, final CountDownLatch countDownLatch, final int i) {
        if (i % 2 == 0) {
            return () -> {
                deadLockEx.addAAndGetSum();
                countDownLatch.countDown();
            };
        }
        return () -> {
            deadLockEx.addBAndGetSum();
            countDownLatch.countDown();
        };
    }
}
