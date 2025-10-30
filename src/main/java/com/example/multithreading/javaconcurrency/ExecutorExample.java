package com.example.multithreading.javaconcurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable {
    private int taskId;
    public Task(int id) { this.taskId = id; }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " is executing task " + taskId);
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}

public class ExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3); // 3 threads in pool

        for (int i = 1; i <= 6; i++) {
            executor.execute(new Task(i));
        }

        executor.shutdown(); // stop accepting new tasks
        System.out.println("All tasks submitted...");
    }
}

