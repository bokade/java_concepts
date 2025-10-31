package com.example.multithreading.concurrencyutilities;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {

    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger(0);

        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " -> " + count.incrementAndGet());
            }
        };

        new Thread(task, "A").start();
        new Thread(task, "B").start();
    }
}

