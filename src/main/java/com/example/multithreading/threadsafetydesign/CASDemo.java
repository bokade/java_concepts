package com.example.multithreading.threadsafetydesign;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger(0);
        counter.compareAndSet(0, 5);
        System.out.println(counter.get());
    }
}
