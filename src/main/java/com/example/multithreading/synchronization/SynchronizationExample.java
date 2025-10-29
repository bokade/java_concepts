package com.example.multithreading.synchronization;



class Printerrr {
    // Static synchronized method — class-level lock
    public static synchronized void print(String msg) {
        System.out.println(Thread.currentThread().getName() + " printing: " + msg);
        try {
            Thread.sleep(500); // simulate printing delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " finished printing: " + msg);
    }
}

class Demo {
    // Instance-level synchronized methods — object-level lock
    public synchronized void method1() {
        System.out.println(Thread.currentThread().getName() + " entered method1");
        method2(); // same thread can call another synchronized method — reentrancy
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + " entered method2");
    }
}

public class SynchronizationExample {
    public static void main(String[] args) {
        // --- Demonstrate Static Synchronization ---
        Runnable printTask = () -> {
            Printerrr.print("Hello from " + Thread.currentThread().getName());
        };

        Thread t1 = new Thread(printTask, "Thread-A");
        Thread t2 = new Thread(printTask, "Thread-B");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Demonstrating Reentrant Locking ---");

        // --- Demonstrate Reentrancy ---
        Demo demoObj = new Demo();

        Thread t3 = new Thread(() -> {
            demoObj.method1();
        }, "Thread-C");

        Thread t4 = new Thread(() -> {
            demoObj.method1();
        }, "Thread-D");

        t3.start();
        t4.start();
    }
}
