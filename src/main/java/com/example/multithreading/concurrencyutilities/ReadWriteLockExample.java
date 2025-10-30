package com.example.multithreading.concurrencyutilities;

import java.util.concurrent.locks.*;

class SharedResource {
    private String data = "Initial";
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void readData() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading: " + data);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void writeData(String newData) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " writing...");
            data = newData;
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}

public class ReadWriteLockExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        new Thread(resource::readData, "Reader-1").start();
        new Thread(() -> resource.writeData("Updated"), "Writer").start();
        new Thread(resource::readData, "Reader-2").start();
    }
}

