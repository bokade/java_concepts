package com.example.multithreading.javaconcurrency;

import java.util.concurrent.*;

public class CompletionServiceExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<String> service = new ExecutorCompletionService<>(executor);

        for (int i = 1; i <= 5; i++) {
            int id = i;
            service.submit(() -> {
                Thread.sleep(id * 500);
                return "Task " + id + " completed";
            });
        }

        for (int i = 0; i < 5; i++) {
            Future<String> f = service.take(); // waits for any completed task
            System.out.println(f.get());
        }

        executor.shutdown();
    }
}

