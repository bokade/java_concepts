package com.example.multithreading.threadcontrol;

class DeadlockDemo {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void methodA() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " acquired lock1, waiting for lock2...");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " acquired lock2");
            }
        }
    }

    public void methodB() {
        synchronized (lock2) {
            System.out.println(Thread.currentThread().getName() + " acquired lock2, waiting for lock1...");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " acquired lock1");
            }
        }
    }
}

public class DeadlockExample {
    public static void main(String[] args) {
        DeadlockDemo demo = new DeadlockDemo();

        Thread t1 = new Thread(() -> demo.methodA(), "Thread-A");
        Thread t2 = new Thread(() -> demo.methodB(), "Thread-B");

        t1.start();
        t2.start();
    }
}

