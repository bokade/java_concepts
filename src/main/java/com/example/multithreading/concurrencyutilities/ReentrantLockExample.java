package com.example.multithreading.concurrencyutilities;

import java.util.concurrent.locks.*;

class SharedData {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
            System.out.println(Thread.currentThread().getName() + " count: " + count);
        } finally {
            lock.unlock();
        }
    }
}

public class ReentrantLockExample {
    public static void main(String[] args) {
        SharedData data = new SharedData();
        Runnable task = () -> { for (int i = 0; i < 3; i++) data.increment(); };

        new Thread(task, "A").start();
        new Thread(task, "B").start();
    }
}

