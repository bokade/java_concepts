package com.example.multithreading.threadcontrol;

class VolatileDemo extends Thread {
    private volatile boolean running = true; // ensures visibility

    public void run() {
        while (running) {
            // busy work
        }
        System.out.println("Thread stopped safely!");
    }

    public void stopRunning() {
        running = false;
    }
}

public class VolatileExample {
    public static void main(String[] args) throws InterruptedException {
        VolatileDemo v = new VolatileDemo();
        v.start();

        Thread.sleep(2000);
        v.stopRunning(); // change visible to thread immediately
    }
}
