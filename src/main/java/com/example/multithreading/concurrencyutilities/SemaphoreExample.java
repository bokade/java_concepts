package com.example.multithreading.concurrencyutilities;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2); // only 2 threads can access at once

        Runnable task = () -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " acquired permit");
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            finally {
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " released permit");
            }
        };

        for (int i = 1; i <= 5; i++) new Thread(task, "Thread-" + i).start();
    }
}

