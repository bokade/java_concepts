package com.example.multithreading.level8thread;

import java.util.concurrent.*;

public class WebServerSimulation {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 6; i++) {
            int reqId = i;
            pool.submit(() -> {
                System.out.println("Processing request " + reqId + " by " + Thread.currentThread().getName());
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            });
        }
        pool.shutdown();
    }
}

