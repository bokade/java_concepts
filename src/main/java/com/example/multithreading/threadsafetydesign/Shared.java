package com.example.multithreading.threadsafetydesign;

class Shared {
    private volatile boolean ready = false;

    public void makeReady() { ready = true; }

    public boolean isReady() { return ready; }
}
