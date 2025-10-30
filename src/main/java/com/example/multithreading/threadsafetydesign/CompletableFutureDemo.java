package com.example.multithreading.threadsafetydesign;

import java.util.concurrent.*;

public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("Running async task...");
            return 5;
        });

        CompletableFuture<Integer> result =
                task.thenApply(x -> x * 2)
                        .thenApply(x -> x + 10);

        System.out.println("Result: " + result.get());
    }
}
