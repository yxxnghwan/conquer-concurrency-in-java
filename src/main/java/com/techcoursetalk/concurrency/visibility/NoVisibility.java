package com.techcoursetalk.concurrency.visibility;

public class NoVisibility {
    public static boolean rocketLaunched = false;

    private static class MissileInterceptor extends Thread {
        @Override
        public void run() {
            while (!rocketLaunched) {/* 대기*/}
            System.out.println("요격");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new MissileInterceptor().start();
        Thread.sleep(5000);
        launchRocket();
    }

    private static void launchRocket() {
        rocketLaunched = true;
    }
}
