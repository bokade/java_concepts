package com.example.multithreading;

class WorkerThread extends Thread {
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(getName() + " running " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadJoinDemo {
    public static void main(String[] args) throws InterruptedException {
        WorkerThread t1 = new WorkerThread();
        WorkerThread t2 = new WorkerThread();

        t1.start();
        t1.join(); // wait till t1 finishes

        t2.start();

        System.out.println("Main finished: " + Thread.currentThread().getName());
    }
}
