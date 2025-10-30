package com.example.multithreading.threadcontrol;

class DeadlockFixed {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void methodFixed() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " got lock1");
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " got lock2 safely");
            }
        }
    }
}

public class DeadlockSolution {
    public static void main(String[] args) {
        DeadlockFixed d = new DeadlockFixed();

        Thread t1 = new Thread(() -> d.methodFixed(), "Thread-A");
        Thread t2 = new Thread(() -> d.methodFixed(), "Thread-B");
        t1.start();
        t2.start();
    }
}

