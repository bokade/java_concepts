package com.example.multithreading.threadcontrol;

class DaemonWorker extends Thread {
    public void run() {
        while (true) {
            System.out.println("Daemon thread working in background...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }
}

public class DaemonThreadExample {
    public static void main(String[] args) {
        DaemonWorker d = new DaemonWorker();
        d.setDaemon(true); // must set before start()
        d.start();

        System.out.println("Main thread doing work...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
        System.out.println("Main thread finished — JVM will exit now!");
    }
}

