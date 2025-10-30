package com.example.multithreading.threadcontrol;

class ThreadLocalExample {
    private static final ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        Runnable task = () -> {
            threadId.set((int) (Math.random() * 100));
            System.out.println(Thread.currentThread().getName() + " ID: " + threadId.get());
        };

        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");
        t1.start(); t2.start();
    }
}

