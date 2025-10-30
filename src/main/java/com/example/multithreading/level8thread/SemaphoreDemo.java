package com.example.multithreading.level8thread;

import java.util.concurrent.*;

class APIRateLimiter {
    private final Semaphore semaphore;

    public APIRateLimiter(int permits) {
        this.semaphore = new Semaphore(permits);
    }

    public void accessAPI(String user) {
        try {
            semaphore.acquire();
            System.out.println(user + " accessing API...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }
}

public class SemaphoreDemo {
    public static void main(String[] args) {
        APIRateLimiter limiter = new APIRateLimiter(2);
        for (int i = 1; i <= 5; i++) {
            String user = "User" + i;
            new Thread(() -> limiter.accessAPI(user)).start();
        }
    }
}

