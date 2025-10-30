package com.example.multithreading.concurrencyutilities;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Runnable worker = () -> {
            System.out.println(Thread.currentThread().getName() + " working...");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            latch.countDown();
        };

        new Thread(worker, "Worker-1").start();
        new Thread(worker, "Worker-2").start();
        new Thread(worker, "Worker-3").start();

        latch.await();
        System.out.println("All workers finished — main thread continues!");
    }
}
