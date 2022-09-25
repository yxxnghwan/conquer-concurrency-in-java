package com.techcoursetalk.concurrency.visibility;

public class NoVisibility {
    public static boolean missileLaunched = false;

    private static class MissileInterceptor extends Thread {
        @Override
        public void run() {
            while (!missileLaunched) {/* 대기*/}
            System.out.println("요격");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        final MissileInterceptor missileInterceptor = new MissileInterceptor();
        missileInterceptor.start();
        Thread.sleep(5000);
        launchMissile();
        missileInterceptor.join();
    }

    private static void launchMissile() {
        missileLaunched = true;
    }
}
