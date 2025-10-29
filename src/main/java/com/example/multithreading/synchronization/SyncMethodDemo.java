package com.example.multithreading.synchronization;

class Counterr {
    private int count = 0;

    public synchronized void increment() { // intrinsic lock on 'this'
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class SyncMethodDemo {
    public static void main(String[] args) throws InterruptedException {
        Counterr c = new Counterr();

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

        System.out.println("Final count (safe): " + c.getCount());
    }
}

