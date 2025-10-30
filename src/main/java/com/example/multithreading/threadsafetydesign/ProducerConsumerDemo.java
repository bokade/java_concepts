package com.example.multithreading.threadsafetydesign;

import java.util.concurrent.*;

class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    public Producer(BlockingQueue<Integer> q) { this.queue = q; }

    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Produced: " + i);
                queue.put(i);
                Thread.sleep(500);
            }
            queue.put(-1); // poison pill
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    public Consumer(BlockingQueue<Integer> q) { this.queue = q; }

    public void run() {
        try {
            int val;
            while ((val = queue.take()) != -1) {
                System.out.println("Consumed: " + val);
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q)).start();
    }
}

