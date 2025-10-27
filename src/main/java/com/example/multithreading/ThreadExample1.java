package com.example.multithreading;

class MyThread extends Thread {
    public void run() {
        // This code runs in a separate thread
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " -> " + i);
            try {
                Thread.sleep(500); // sleep for 500ms
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class ThreadExample1 {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();  // thread object
        MyThread t2 = new MyThread();

        // start() → tells JVM to call run() in a new thread
        t1.start();
        t2.start();

        System.out.println("Main thread ends: " + Thread.currentThread().getName());
    }
}

