package com.example.collections.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");

        Thread writer = new Thread(() -> {
            list.add("C");
            System.out.println("Added C");
        });

        Thread reader = new Thread(() -> {
            for (String s : list) {
                System.out.println("Read: " + s);
            }
        });

        writer.start();
        reader.start();
    }
}
