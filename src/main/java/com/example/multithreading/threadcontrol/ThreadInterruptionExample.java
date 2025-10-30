package com.example.multithreading.threadcontrol;

class InterruptDemo extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(getName() + " interrupted! Exiting safely...");
                break;
            }
            System.out.println(getName() + " running step " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted during sleep!");
                break;
            }
        }
    }
}

public class ThreadInterruptionExample {
    public static void main(String[] args) throws InterruptedException {
        InterruptDemo t1 = new InterruptDemo();
        t1.start();

        Thread.sleep(2500); // let thread run a bit
        t1.interrupt();     // send interrupt signal
    }
}
