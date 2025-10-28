package com.example.multithreading;
class MyThreadd extends Thread {
    public MyThreadd(String name) {
        super(name); // set thread name
    }

    @Override
    public void run() {
        System.out.println(getName() + " started...");
        try {
            for (int i = 1; i <= 3; i++) {
                System.out.println(getName() + " - Count: " + i);
                Thread.sleep(500); // pause for 500ms
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupted!");
        }
        System.out.println(getName() + " finished!");
    }
}

// Second thread by implementing Runnable interface
class MyRunnablee implements Runnable {
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " started...");
        try {
            for (int i = 1; i <= 3; i++) {
                System.out.println(threadName + " - Step: " + i);
                Thread.sleep(700);
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " interrupted!");
        }
        System.out.println(threadName + " finished!");
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        System.out.println("Main thread started...");

        // Thread 1 (by extending Thread)
        MyThreadd t1 = new MyThreadd("Thread-Extends");
        t1.setPriority(Thread.MAX_PRIORITY); // 10

        // Thread 2 (by implementing Runnable)
        Thread t2 = new Thread(new MyRunnablee(), "Thread-Runnable");
        t2.setPriority(Thread.NORM_PRIORITY); // 5

        // Start both threads
        t1.start();
        t2.start();

        // Check if threads are alive
        System.out.println("Is t1 alive? " + t1.isAlive());
        System.out.println("Is t2 alive? " + t2.isAlive());

        try {
            // Wait for t1 and t2 to finish before continuing main
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted!");
        }

        // After both threads are done
        System.out.println("Is t1 alive after join? " + t1.isAlive());
        System.out.println("Is t2 alive after join? " + t2.isAlive());

        System.out.println("Main thread finished!");
    }
}
