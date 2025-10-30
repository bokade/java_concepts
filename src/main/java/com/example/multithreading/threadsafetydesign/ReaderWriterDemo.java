package com.example.multithreading.threadsafetydesign;

import java.util.concurrent.locks.*;

class SharedData {
    private int data = 0;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void write(int value) {
        lock.writeLock().lock();
        try {
            data = value;
            System.out.println(Thread.currentThread().getName() + " wrote " + value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void read() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " read " + data);
        } finally {
            lock.readLock().unlock();
        }
    }
}

public class ReaderWriterDemo {
    public static void main(String[] args) {
        SharedData data = new SharedData();
        new Thread(() -> data.write(10), "Writer").start();
        new Thread(data::read, "Reader1").start();
        new Thread(data::read, "Reader2").start();
    }
}

