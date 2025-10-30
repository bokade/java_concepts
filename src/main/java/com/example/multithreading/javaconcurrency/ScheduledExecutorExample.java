package com.example.multithreading.javaconcurrency;

import java.util.concurrent.*;

public class ScheduledExecutorExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Task executed at " + System.currentTimeMillis());
        scheduler.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS); // start after 1s, repeat every 2s
    }
}

