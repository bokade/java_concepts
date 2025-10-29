package com.example.multithreading.synchronization;

class Counter {
    int count = 0;

    public void increment() {
        count++; // not atomic
    }
}

//value overlap due to race condition
public class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) c.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) c.increment();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count: " + c.count);
    }
}

