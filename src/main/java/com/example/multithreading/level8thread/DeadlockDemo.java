package com.example.multithreading.level8thread;

class Resource {}
public class DeadlockDemo {
    public static void main(String[] args) {
        final Resource r1 = new Resource();
        final Resource r2 = new Resource();

        Thread t1 = new Thread(() -> {
            synchronized (r1) {
                System.out.println("T1 locked R1");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (r2) { System.out.println("T1 locked R2"); }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (r2) {
                System.out.println("T2 locked R2");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (r1) { System.out.println("T2 locked R1"); }
            }
        });

        t1.start(); t2.start();
    }
}

