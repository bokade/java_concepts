package com.example.multithreading.concurrencyutilities;

import java.util.concurrent.*;

public class BlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i);
                    System.out.println("Produced " + i);
                }
            } catch (InterruptedException e) {}
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    int val = queue.take();
                    System.out.println("Consumed " + val);
                }
            } catch (InterruptedException e) {}
        });

        producer.start();
        consumer.start();
    }
}

