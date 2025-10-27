package com.example.multithreading;

class MyRunnable implements Runnable {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadExample2 {
    public static void main(String[] args) {
        Runnable r = new MyRunnable();
        Thread t1 = new Thread(r, "Worker-1");
        Thread t2 = new Thread(r, "Worker-2");

        t1.start();
        t2.start();

        System.out.println("Main: " + Thread.currentThread().getName());
    }
}

