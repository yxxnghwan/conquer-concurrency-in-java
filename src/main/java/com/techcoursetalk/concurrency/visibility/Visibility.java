package com.techcoursetalk.concurrency.visibility;

public class Visibility {
    public volatile static boolean ready = false;
    public volatile static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {/* 대기*/}
            System.out.println("number = " + number);
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            number++;
            new ReaderThread().start();
        }
        ready = true;
    }
}