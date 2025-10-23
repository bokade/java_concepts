package com.example.collections.concurrent;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // Writer Thread
        Thread writer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                map.put("Key" + i, i);
                System.out.println("Added: Key" + i);
            }
        });

        // Reader Thread
        Thread reader = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Read: Key" + i + " = " + map.get("Key" + i));
            }
        });

        writer.start();
        reader.start();
    }
}
