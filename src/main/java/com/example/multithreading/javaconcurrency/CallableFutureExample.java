package com.example.multithreading.javaconcurrency;

import java.util.concurrent.*;

class SumTask implements Callable<Integer> {
    private int n;
    public SumTask(int n) { this.n = n; }

    public Integer call() {
        int sum = 0;
        for (int i = 1; i <= n; i++) sum += i;
        return sum;
    }
}

public class CallableFutureExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(new SumTask(10));

        System.out.println("Task submitted... doing other work...");
        System.out.println("Result = " + future.get()); // blocks until result ready

        executor.shutdown();
    }
}

