package com.techcoursetalk.concurrency.deadlock;

public class DeadLockEx {
    public static Integer a = 0;
    public static Integer b = 0;

    public void addAAndGetSum() {
        synchronized (a) {
            a++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
            synchronized (b) {
                System.out.println("a + b = " + (a + b));
            }
        }
    }
    public void addBAndGetSum() {
        synchronized (b) {
            b++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
            synchronized (a) {
                System.out.println("a + b = " + (a + b));
            }
        }
    }
}
