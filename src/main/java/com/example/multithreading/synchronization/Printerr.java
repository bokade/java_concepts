package com.example.multithreading.synchronization;

class Printerr {
    public static synchronized void print(String msg) {
        System.out.println(Thread.currentThread().getName() + " printing: " + msg);
    }
}

