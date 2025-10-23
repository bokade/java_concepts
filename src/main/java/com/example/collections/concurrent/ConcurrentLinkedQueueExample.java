package com.example.collections.concurrent;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueExample {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                queue.offer("Task " + i);
                System.out.println("Produced: Task " + i);
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                String task = queue.poll();
                if (task != null)
                    System.out.println("Consumed: " + task);
            }
        });

        producer.start();
        consumer.start();
    }
}

